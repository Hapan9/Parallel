using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab6
{
    [Serializable]
    public class MatrixRowsDto
    {
        public int[][] FirstRows { get; set; }
        public int[][] Second { get; set; }
        public int Offset { get; set; }
    }
}
