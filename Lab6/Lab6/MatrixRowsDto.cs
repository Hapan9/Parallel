using System;

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