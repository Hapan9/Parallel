﻿using System;
using System.Collections.Generic;
using System.Diagnostics;
using Environment = MPI.Environment;

namespace Lab7
{
    internal class Program
    {
        private const int Master = 0;

        private static void Main(string[] args)
        {
            OneToMany(args);
        }

        private static void ManyToMany(string[] args)
        {
            var args1 = args;
            Environment.Run(ref args, comm =>
            {
                long initializationTime = 0;
                Stopwatch stopwatch = null;
                var rank = Convert.ToInt32(args1[0]);

                if (rank % comm.Size != 0)
                    throw new ArgumentException("Workers count is not valid for matrix size");

                var rowsPerNode = rank / comm.Size;

                if (comm.Rank == Master)
                {
                    stopwatch = new Stopwatch();
                    stopwatch.Start();
                }

                var secondBlock = CreateMatrix(rowsPerNode, rank);
                var firstBlock = CreateMatrix(rowsPerNode, rank);

                if (comm.Rank == Master)
                {
                    if (stopwatch != null)
                    {
                        stopwatch.Stop();
                        initializationTime = stopwatch.ElapsedMilliseconds;
                        Console.WriteLine("Initialization time elapsed - " + initializationTime);
                        stopwatch.Restart();
                    }
                }

                var second = comm.AllgatherFlattened(secondBlock, secondBlock.Length);

                var resultBlock = Multiply(firstBlock, second);

                comm.GatherFlattened(resultBlock, Master);

                if (comm.Rank != Master) return;
                if (stopwatch == null) return;

                stopwatch.Stop();
                Console.WriteLine("Multiplication time elapsed - " + stopwatch.ElapsedMilliseconds);
                Console.WriteLine("Time elapsed - " + (initializationTime + stopwatch.ElapsedMilliseconds));
            });
        }

        private static void OneToMany(string[] args)
        {
            var args1 = args;
            Environment.Run(ref args, comm =>
            {
                int[][] first = null, second = null;
                long initializationTime = 0;
                Stopwatch stopwatch = null;
                var rank = Convert.ToInt32(args1[0]);

                if (rank % comm.Size != 0)
                    throw new ArgumentException("Workers count is not valid for matrix size");

                if (comm.Rank == Master)
                {
                    stopwatch = new Stopwatch();
                    stopwatch.Start();

                    first = CreateMatrix(rank);
                    second = CreateMatrix(rank);

                    stopwatch.Stop();
                    initializationTime = stopwatch.ElapsedMilliseconds;
                    Console.WriteLine("Initialization time elapsed - " + initializationTime);
                    stopwatch.Restart();
                }

                var rowsPerNode = rank / comm.Size;

                var block = comm.ScatterFromFlattened(first, rowsPerNode, Master);
                comm.Broadcast(ref second, Master);

                var resultBlock = Multiply(block, second);

                comm.GatherFlattened(resultBlock, Master);

                if (comm.Rank != Master) return;
                if (stopwatch == null) return;

                stopwatch.Stop();
                Console.WriteLine("Multiplication time elapsed - " + stopwatch.ElapsedMilliseconds);
                Console.WriteLine("Time elapsed - " + (initializationTime + stopwatch.ElapsedMilliseconds));
            });
        }

        private static string[] Test(string[] args)
        {
            var args1 = args;
            Environment.Run(ref args, comm =>
            {
                if (comm.Size < 2)
                {
                    throw new Exception("Need at least two MPI tasks. Quitting...");
                }

                if (comm.Rank == Master)
                {
                    var rank = Convert.ToInt32(args1[0]);
                    var first = CreateMatrix(rank);
                    var second = CreateMatrix(rank);
                    int[][] result = null;

                    comm.Scatter(first, Master);

                    comm.Broadcast(ref second, Master);

                    comm.Gather(new int[0], Master, ref result);
                }
                else
                {
                    var row = comm.Scatter(new int[0][], Master);

                    if (row == null)
                        Console.WriteLine($"Rank {comm.Rank}: First matrix rows not received");

                    int[][] second = null;
                    comm.Broadcast(ref second, Master);

                    if (second == null)
                        Console.WriteLine($"Rank {comm.Rank}: Second matrix not received");

                    if (second != null)
                        Console.WriteLine(
                            $"Rank {comm.Rank}: {string.Join(", ", row ?? throw new InvalidOperationException())}; {second.Length}");
                }
            });
            return args;
        }

        private static void PrintMatrix(int[][] matrix)
        {
            foreach (var elem in matrix)
            {
                for (var j = 0; j < matrix[0].Length; j++) Console.Write(elem[j] + " ");
                Console.WriteLine();
            }
        }

        private static int[][] CreateMatrix(int rank)
        {
            var matrix = CreateEmptyMatrix(rank);
            for (var i = 0; i < rank; i++)
            for (var j = 0; j < rank; j++)
                matrix[i][j] = new Random().Next(100);
            return matrix;
        }

        private static int[][] CreateMatrix(int rows, int columns)
        {
            var matrix = CreateEmptyMatrix(rows, columns);
            for (var i = 0; i < rows; i++)
            for (var j = 0; j < columns; j++)
                matrix[i][j] = new Random().Next(100);
            return matrix;
        }

        private static int[][] CreateEmptyMatrix(int rank)
        {
            var matrix = new int[rank][];
            for (var i = 0; i < rank; i++) matrix[i] = new int[rank];
            return matrix;
        }

        private static int[][] CreateEmptyMatrix(int rows, int columns)
        {
            var matrix = new int[rows][];
            for (var i = 0; i < rows; i++) matrix[i] = new int[columns];
            return matrix;
        }

        private static bool MatricesEqual(IReadOnlyList<int[]> expected, IReadOnlyList<int[]> actual)
        {
            for (var i = 0; i < expected.Count; i++)
            for (var j = 0; j < expected[0].Length; j++)
                if (expected[i][j] != actual[i][j])
                    return false;
            return true;
        }

        public static int[][] Multiply(int[][] first, int[][] second)
        {
            var matrix = CreateEmptyMatrix(first.Length, second[0].Length);
            for (var i = 0; i < matrix.Length; i++)
            for (var j = 0; j < matrix[0].Length; j++)
            {
                matrix[i][j] = 0;
                for (var k = 0; k < second.Length; k++) matrix[i][j] += first[i][k] * second[k][j];
            }

            return matrix;
        }
    }
}