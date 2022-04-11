using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BLL.Models;

namespace BLL.Interfaces
{
    public interface IMatrixCalculationService
    {
        public void MatrixMultiplication(ThreeResultMatrix item);

        public List<List<int>> GenerateMatrix(int raw, int column);
    }
}
