using System;

namespace Lab6
{
    [Serializable]
    public class MultiplyResultDto
    {
        public int Offset { get; set; }
        public int[][] Result { get; set; }
    }
}