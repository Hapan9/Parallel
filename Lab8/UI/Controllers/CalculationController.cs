using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BLL.Interfaces;
using BLL.Models;

namespace UI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CalculationController : ControllerBase
    {
        private readonly IMatrixCalculationService _matrixCalculationService;

        public CalculationController(IMatrixCalculationService matrixCalculationService)
        {
            _matrixCalculationService = matrixCalculationService;
        }

        [HttpPost]
        public IActionResult PostWithParams([FromBody]List<List<List<int>>> value)
        {
            try
            {
                var item = new ThreeResultMatrix {FirstMatrix = value[0], SecondMatrix = value[1]};
                var startTime = DateTime.Now.ToFileTimeUtc();
                _matrixCalculationService.MatrixMultiplication(item);
                item.Time = DateTime.Now.ToFileTimeUtc() - startTime;
                return new JsonResult(item);
            }
            catch (Exception ex)
            {
                return BadRequest(ex);
            }
        }

        [HttpGet("{raw:int}/{column:int}")]
        public IActionResult GetWithPara([FromRoute]int raw, [FromRoute] int column)
        {
            try
            {
                var item = new ThreeResultMatrix { FirstMatrix = _matrixCalculationService.GenerateMatrix(raw, column), SecondMatrix = _matrixCalculationService.GenerateMatrix(raw, column) };
                var startTime = DateTime.Now.ToFileTimeUtc();
                _matrixCalculationService.MatrixMultiplication(item);
                item.Time = DateTime.Now.ToFileTimeUtc() - startTime;
                return new JsonResult(item);
            }
            catch (Exception ex)
            {
                return BadRequest(ex);
            }
        }
    }
}
