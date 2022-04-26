using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;
using MPI;
using Environment = MPI.Environment;

namespace Lab6
{
    internal class Program
    {
        private const int FromMaster = 1;
        private const int FromWorker = 2;
        private const int Master = 0;

        private static void Main(string[] args)
        {
            MultiProcessSample(args);
            //MultiProcessImmediateSample(args);
        }

        private static void MultiProcessSample(string[] args)
        {
            var args1 = args;
            Environment.Run(ref args, comm =>
            {
                while (true)
                {
                    if (comm.Size >= 2)
                        break;
                    Console.WriteLine("Need at least two MPI tasks. Tasks - " + comm.Size);
                    Thread.Sleep(1000);
                }

                if (comm.Rank == Master)
                {
                    Console.WriteLine("Blocking");
                    Console.WriteLine("Matrices rank - " + args1[0]);
                    Console.WriteLine("Processes - " + comm.Size);

                    var rank = Convert.ToInt32(args1[0]);
                    var first = CreateMatrix(rank);
                    var second = CreateMatrix(rank);
                    var result = CreateEmptyMatrix(rank);

                    var watch = new Stopwatch();
                    watch.Start();

                    var workersCount = comm.Size - 1;
                    var rowsPerWorker = first.Length / workersCount;

                    if (first.Length % workersCount != 0)
                        throw new ArgumentException("Workers count is not valid for matrix size");

                    for (var i = 0; i < workersCount; i++)
                    {
                        var offset = rowsPerWorker * i;
                        var request = new MatrixRowsDto
                        {
                            FirstRows = first[offset..(offset + rowsPerWorker)],
                            Second = second,
                            Offset = offset
                        };
                        comm.Send(request, i + 1, FromMaster);
                    }

                    for (var i = 0; i < workersCount; i++)
                    {
                        var response = comm.Receive<MultiplyResultDto>(i + 1, FromWorker);
                        var offset = response.Offset;

                        for (var j = 0; j < response.Result.Length; j++) 
                            result[offset + j] = response.Result[j];
                    }

                    watch.Stop();
                    Console.WriteLine("Time for single: " + watch.ElapsedMilliseconds);
                }
                else
                {
                    var data = comm.Receive<MatrixRowsDto>(Master, FromMaster);
                    var result = Multiply(data.FirstRows, data.Second);
                    comm.Send(new MultiplyResultDto {Result = result, Offset = data.Offset}, Master, FromWorker);
                }
            });
        }

        private static void MultiProcessImmediateSample(string[] args)
        {
            var args1 = args;
            Environment.Run(ref args, comm =>
            {
                while (true)
                {
                    if (comm.Size >= 2)
                        break;
                    Console.WriteLine("Need at least two MPI tasks. Tasks - " + comm.Size);
                    Thread.Sleep(1000);
                }

                if (comm.Rank == Master)
                {
                    Console.WriteLine("Immediate. Let's go.");
                    Console.WriteLine("Matrices rank - " + args1[0]);
                    Console.WriteLine("Processes - " + comm.Size);

                    var rank = Convert.ToInt32(args1[0]);
                    var first = CreateMatrix(rank);
                    var second = CreateMatrix(rank);
                    var result = CreateEmptyMatrix(rank);

                    var watch = new Stopwatch();
                    watch.Start();

                    var workersCount = comm.Size - 1;
                    var rowsPerWorker = first.Length / workersCount;

                    if (first.Length % workersCount != 0)
                        throw new ArgumentException("Workers count is not valid for matrix size");

                    var requests = new RequestList();

                    for (var i = 0; i < workersCount; i++)
                    {
                        var offset = rowsPerWorker * i;
                        var value = new MatrixRowsDto
                        {
                            FirstRows = first[offset..(offset + rowsPerWorker)],
                            Second = second,
                            Offset = offset
                        };
                        var request = comm.ImmediateSend(value, i + 1, FromMaster);
                        requests.Add(request);
                    }

                    requests.WaitAll();

                    var receiveRequests = new RequestList();

                    for (var i = 0; i < workersCount; i++)
                    {
                        var receiveRequest = comm.ImmediateReceive<MultiplyResultDto>(i + 1, FromWorker, response =>
                        {
                            var offset = response.Offset;

                            for (var j = 0; j < response.Result.Length; j++) result[offset + j] = response.Result[j];
                        });

                        receiveRequests.Add(receiveRequest);
                    }

                    receiveRequests.WaitAll();

                    watch.Stop();
                    Console.WriteLine("Time for parallel: " + watch.ElapsedMilliseconds);
                }
                else
                {
                    var receiveRequest = comm.ImmediateReceive<MatrixRowsDto>(Master, FromMaster);
                    receiveRequest.Wait();
                    var data = (MatrixRowsDto) receiveRequest.GetValue();
                    var result = new MultiplyResultDto
                        {Result = Multiply(data.FirstRows, data.Second), Offset = data.Offset};
                    comm.ImmediateSend(result, Master, FromWorker);
                }
            });
        }

        private static int[][] CreateMatrix(int rank)
        {
            var matrix = CreateEmptyMatrix(rank);
            for (var i = 0; i < rank; i++)
            for (var j = 0; j < rank; j++)
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