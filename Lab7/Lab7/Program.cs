using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace Lab7
{
    class Program
    {
        const int Master = 0;
        
        static void Main(string[] args)
        {
            OneToMany(args);
        }

        private static void ManyToMany(string[] args)
        {
            MPI.Environment.Run(ref args, comm =>
            {
                int[][] first = null, second = null, result = null, expected = null, firstBlock, secondBlock;
                long initializationTime = 0;
                Stopwatch stopwatch = null;
                var rank = Convert.ToInt32(args[0]);

                if (rank % comm.Size != 0)
                    throw new ArgumentException("Workers count is not valid for matrix size");

                var rowsPerNode = rank / comm.Size;

                if (comm.Rank == Master)
                {
                    stopwatch = new Stopwatch();
                    stopwatch.Start();
                }

                secondBlock = createMatrix(rowsPerNode, rank);
                firstBlock = createMatrix(rowsPerNode, rank);

                if (comm.Rank == Master)
                {
                    stopwatch.Stop();
                    initializationTime = stopwatch.ElapsedMilliseconds;
                    Console.WriteLine("Initialization time elapsed - " + initializationTime);
                    stopwatch.Restart();
                }

                second = comm.AllgatherFlattened(secondBlock, secondBlock.Length);

                var resultBlock = multiply(firstBlock, second);

                result = comm.GatherFlattened(resultBlock, Master);

                if (comm.Rank == Master)
                {
                    stopwatch.Stop();
                    Console.WriteLine("Multiplication time elapsed - " + stopwatch.ElapsedMilliseconds);
                    Console.WriteLine("Time elapsed - " + (initializationTime + stopwatch.ElapsedMilliseconds));
                }
            });
        }

        private static void OneToMany(string[] args)
        {
            MPI.Environment.Run(ref args, comm =>
            {
                int[][] first = null, second = null, result = null, expected = null, block;
                long initializationTime = 0;
                Stopwatch stopwatch = null;
                var rank = Convert.ToInt32(args[0]);

                if (rank % comm.Size != 0)
                    throw new ArgumentException("Workers count is not valid for matrix size");

                if (comm.Rank == Master)
                {
                    stopwatch = new Stopwatch();
                    stopwatch.Start();

                    first = createMatrix(rank);
                    second = createMatrix(rank);
                    
                    stopwatch.Stop();
                    initializationTime = stopwatch.ElapsedMilliseconds;
                    Console.WriteLine("Initialization time elapsed - " + initializationTime);
                    stopwatch.Restart();
                }

                var rowsPerNode = rank / comm.Size;

                block = comm.ScatterFromFlattened(first, rowsPerNode, Master);
                comm.Broadcast(ref second, Master);

                var resultBlock = multiply(block, second);

                result = comm.GatherFlattened(resultBlock, Master);

                if (comm.Rank == Master)
                {
                    stopwatch.Stop();
                    Console.WriteLine("Multiplication time elapsed - " + stopwatch.ElapsedMilliseconds);
                    Console.WriteLine("Time elapsed - " + (initializationTime + stopwatch.ElapsedMilliseconds));
                }
            });
        }

        private static string[] Test(string[] args)
        {
            MPI.Environment.Run(ref args, comm =>
            {
                if (comm.Size < 2)
                {
                    throw new Exception("Need at least two MPI tasks. Quitting...");
                }
                else if (comm.Rank == Master)
                {
                    var rank = Convert.ToInt32(args[0]);
                    var first = createMatrix(rank);
                    var second = createMatrix(rank);
                    int[][] result = null;

                    comm.Scatter(first, Master);

                    comm.Broadcast(ref second, Master);

                    comm.Gather(new int[0], Master, ref result);
                }
                else
                {
                    int[] row = comm.Scatter(new int[0][], Master);

                    if (row == null)
                        Console.WriteLine($"Rank {comm.Rank}: First matrix rows not received");

                    int[][] second = null;
                    comm.Broadcast(ref second, Master);

                    if (second == null)
                        Console.WriteLine($"Rank {comm.Rank}: Second matrix not received");

                    Console.WriteLine($"Rank {comm.Rank}: {string.Join(", ", row)}; {second.Length}");
                }
            });
            return args;
        }

        private static void printMatrix(int[][] matrix)
        {
            for (int i = 0; i < matrix.Length; i++)
            {
                for (int j = 0; j < matrix[0].Length; j++)
                {
                    Console.Write(matrix[i][j] + " ");
                }
                Console.WriteLine();
            }
        }

        private static int[][] createMatrix(int rank)
        {
            int[][] matrix = createEmptyMatrix(rank);
            for (int i = 0; i < rank; i++)
            {
                for (int j = 0; j < rank; j++)
                {
                    matrix[i][j] = new Random().Next(100);
                }
            }
            return matrix;
        }

        private static int[][] createMatrix(int rows, int columns)
        {
            int[][] matrix = createEmptyMatrix(rows, columns);
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < columns; j++)
                {
                    matrix[i][j] = new Random().Next(100);
                }
            }
            return matrix;
        }

        private static int[][] createEmptyMatrix(int rank)
        {
            int[][] matrix = new int[rank][];
            for (int i = 0; i < rank; i++)
            {
                matrix[i] = new int[rank];
            }
            return matrix;
        }

        private static int[][] createEmptyMatrix(int rows, int columns)
        {
            int[][] matrix = new int[rows][];
            for (int i = 0; i < rows; i++)
            {
                matrix[i] = new int[columns];
            }
            return matrix;
        }

        private static bool matricesEqual(int[][] expected, int[][] actual)
        {
            for (int i = 0; i < expected.Length; i++)
            {
                for (int j = 0; j < expected[0].Length; j++)
                {
                    if (expected[i][j] != actual[i][j])
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        public static int[][] multiply(int[][] first, int[][] second)
        {
            int[][] matrix = createEmptyMatrix(first.Length, second[0].Length);
            for (int i = 0; i < matrix.Length; i++)
            {
                for (int j = 0; j < matrix[0].Length; j++)
                {
                    matrix[i][j] = 0;
                    for (int k = 0; k < second.Length; k++)
                    {
                        matrix[i][j] += first[i][k] * second[k][j];
                    }
                }
            }
            return matrix;
        }
    }
}
