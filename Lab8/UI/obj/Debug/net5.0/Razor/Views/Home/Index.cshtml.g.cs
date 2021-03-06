#pragma checksum "C:\Users\Hapan9\Documents\GitHub\Parallel\Lab8\UI\Views\Home\Index.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "7e79c7aa4b58f98ccadad2fe236bae057de715a0"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Home_Index), @"mvc.1.0.view", @"/Views/Home/Index.cshtml")]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#nullable restore
#line 1 "C:\Users\Hapan9\Documents\GitHub\Parallel\Lab8\UI\Views\_ViewImports.cshtml"
using UI;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "C:\Users\Hapan9\Documents\GitHub\Parallel\Lab8\UI\Views\_ViewImports.cshtml"
using UI.Models;

#line default
#line hidden
#nullable disable
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"7e79c7aa4b58f98ccadad2fe236bae057de715a0", @"/Views/Home/Index.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"52d79ad08d11418ded2b13adb4a9b2619d15bb23", @"/Views/_ViewImports.cshtml")]
    public class Views_Home_Index : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<dynamic>
    {
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            WriteLiteral(@"<script>
    function drawDefaultMatrix(sendPost) {
        var firstArray = generateIntArray(document.getElementById(""rawInt"").value, document.getElementById(""columnInt"").value);
        var secondArray = generateIntArray(document.getElementById(""rawInt"").value, document.getElementById(""columnInt"").value);

        if (sendPost) {
            sendPostRequest(JSON.stringify([firstArray, secondArray]));
        }

        drawMatrix(""matrix1"", firstArray);
        drawMatrix(""matrix2"", secondArray);
    }

    function drawMatrix(elementName, intArray) {
        var table = document.createElement(""table"");
        var i;
        for (i = 0; i < intArray.length; i++){
            var tr = document.createElement(""tr"");
            for (var j = 0; j < intArray[i].length; j++) {
                var td = document.createElement(""td"");
                td.innerHTML = intArray[i][j];
                tr.appendChild(td);
            }
            table.appendChild(tr);
        }
        table.set");
            WriteLiteral(@"Attribute(""border"", 1)
        document.getElementById(elementName).innerHTML = """";
        document.getElementById(elementName).appendChild(table);
    }

    function generateIntArray(row, column) {
        var array = [];
        for (var i = 0; i < row; i++) {
            array[i] = [];
            for (var j = 0; j < column; j++) {
                array[i][j] = Math.floor(Math.random() * 3 + 1);
            }
        }
        return array;
    }

    function sendPostRequest(json) {
        var xhr = new XMLHttpRequest();
        var url = ""https://localhost:44379/api/Calculation/"";
        xhr.open(""POST"", url, true);
        xhr.setRequestHeader(""Content-Type"", ""application/json"");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var result = JSON.parse(xhr.responseText);
                drawMatrix(""matrix3"", result.finalMatrix);
                document.getElementById(""operationTime"").innerHTML = resu");
            WriteLiteral(@"lt.time;
                document.getElementById(""operationTime"").focus();
            }
        };
        xhr.send(json);
    }

    function sendGetRequest() {
        var xhr = new XMLHttpRequest();
        var url = 'https://localhost:44379/api/Calculation/' + document.getElementById(""rawInt"").value + '/' + document.getElementById(""columnInt"").value;
        xhr.open(""GET"", url, true);
        xhr.setRequestHeader(""Content-Type"", ""application/json"");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var result = JSON.parse(xhr.responseText);
                drawMatrix(""matrix1"", result.firstMatrix);
                drawMatrix(""matrix2"", result.secondMatrix);
                drawMatrix(""matrix3"", result.finalMatrix);
                document.getElementById(""operationTime"").innerHTML = result.time;
                document.getElementById(""operationTime"").focus();
            }
        };
        xhr.send();
  ");
            WriteLiteral(@"  }
</script>

<style>
    table {
        text-align: center;
        font-size: 33px;
        border-collapse: collapse;
        margin: 2px;
        align-items: center;
    }

    td {
        min-width: 3px;
        min-height: 3px;
    }
    th {
        min-width: 35px;
        min-height: 35px;
        font-size: 35px;
        margin: 10px;
    }
</style>


<table style=""margin: auto"" border=""2"">
    <thead>
        <tr>
            <th>Matrix A</th>
            <th>Matrix B</th>
        </tr>
    </thead>
    <tr>
        <td>
            <div id=""matrix1""></div>
        </td>
        <td>
            <div id=""matrix2""></div>
        </td>
    </tr>
    <tr>
        <th colspan=""2"">
            A * B
        </th>
    </tr>
    <tr>
        <td colspan=""2"" style=""margin: auto;"">
            <div id=""matrix3""></div>
            <div id=""operationTime"" style=""font-size: 35px;""></div>
        </td>
    </tr>
    <tr>
    <tr>
        <td colspan=""2"">");
            WriteLiteral(@"
            <table style=""margin-left: auto; margin-right: auto;"">
                <tr><th>Raw</th><td><input id=""rawInt"" type=""number"" value=""5"" /></td></tr>
                <tr><th>Column</th><td><input id=""columnInt"" type=""number"" value=""5"" /></td></tr>
                <tr>
                    <th colspan=""2"">
                        <input onclick=""drawDefaultMatrix(false)"" type=""submit"" value=""?????????????????? ??????????????"" />
                    </th>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td style=""margin: auto""><input value=""???????????????????? ????????????????"" type=""submit"" onclick=""drawDefaultMatrix(true)"" /></td>
        <td style=""margin: auto""><input value=""?????? ??????????????"" type=""submit"" onclick=""sendGetRequest()"" /></td>
    </tr>
</table>

");
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<dynamic> Html { get; private set; }
    }
}
#pragma warning restore 1591
