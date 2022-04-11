using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BLL.Models
{
    public class ThreeResultMatrix
    {
        public List<List<int>> FinalMatrix { get; set; }
        public List<List<int>> FirstMatrix { get; set; }
        public List<List<int>> SecondMatrix { get; set; }
        public long Time { get; set; }

        public ThreeResultMatrix()
        {
            FirstMatrix = new List<List<int>>();
            SecondMatrix = new List<List<int>>();
            FinalMatrix = new List<List<int>>();
        }
    }
}
