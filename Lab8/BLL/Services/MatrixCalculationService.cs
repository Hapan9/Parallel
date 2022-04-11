using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using BLL.Interfaces;
using BLL.Models;

namespace BLL.Services
{
    public class MatrixCalculationService:IMatrixCalculationService
    {
        public void MatrixMultiplication(ThreeResultMatrix item)
        {
            if(item.FirstMatrix.Count != item.SecondMatrix.Count)
            {
                throw new InvalidDataException();
            }

            if (item.FirstMatrix.Select(r => r.Count).Any(c => c != item.FirstMatrix.Count))
            {
                throw new InvalidDataException();
            }

            if (item.SecondMatrix.Select(r => r.Count).Any(c => c != item.SecondMatrix.Count))
            {
                throw new InvalidDataException();
            }

            var tasks = new List<Task>();

            for (var i = 0; i < item.FirstMatrix.Count; i++)
            {
                item.FinalMatrix.Add(new List<int>());
                for (var j = 0; j < item.FirstMatrix[0].Count; j++)
                {
                    item.FinalMatrix[i].Add(0);
                    tasks.Add(CalculateRowColumn(i, j));
                }
            }

            tasks.ForEach(t=> t.Start());
            Task.WaitAll(tasks.ToArray());

            Task CalculateRowColumn(int raw, int column)
            {
                return new(() => item.FinalMatrix[raw][column] = item.FinalMatrix.Select((t, i) => item.FirstMatrix[raw][i] * item.SecondMatrix[i][column])
                    .ToList().Sum());
            }
        }
        public List<List<int>> GenerateMatrix(int raw, int column)
        {
            var rnd = new Random();
            var result = new List<List<int>>();

            for (var i = 0; i < raw; i++)
            {
                result.Add(new List<int>());
                for (var j = 0; j < column; j++)
                {
                    result[i].Add(rnd.Next(1, 4));
                }
            }

            return result;
        }
    }
}
