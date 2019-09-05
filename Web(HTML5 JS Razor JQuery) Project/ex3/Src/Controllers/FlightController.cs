using Ex3.Models;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Threading;
using System.Web.Mvc;

namespace Ex3.Controllers
{
    public class FlightController : Controller
    {
        // Default start screen
        public ActionResult Index()
        {
            return View();
        }

        [HttpGet]
        //display for ip and port or display loaded data from file - with time in second.
        public ActionResult display1Or4(string ipOrFileName, int portOrTime)
        {
            //check if display1 - with ip and port or display 2- with file name and time in seconds
            int cnt = 0;
            int i;
            for (i = 0; i < ipOrFileName.Length; i++)
            {
                if (ipOrFileName[i] == '.')
                {
                    cnt++;
                }
            }
            if (cnt == 3)//ip port - display1
            {
                ViewBag.cond = 1;
                ViewBag.cond2 = 0;
                ViewBag.ms = 1;
                ViewBag.times = int.MaxValue;
                Connect c = Connect.Instance.connect(ipOrFileName, portOrTime);
                Route r = Route.Instance;
                Thread.Sleep(2000);
                return View();
            }
            else //file and timeInSec - display4
            {
                ViewBag.cond = 0;
                ViewBag.cond2 = 1;
                ViewBag.ms = 1000 / portOrTime;
                ViewBag.times = int.MaxValue;
                Route.Instance.Clear();
                FileHandler.Instance.Clear();
                FileHandler.Instance.FileName = Server.MapPath(@"~/" + ipOrFileName + ".txt");
                Route r = Route.Instance;
                Thread.Sleep(2000);
                ViewBag.fileName = ipOrFileName;
                return View();
            }
        }

        [HttpGet]
        public ActionResult display2(string ip, int port, int time)
        {
            Route.Instance.Clear();
            Connect c = Connect.Instance.connect(ip, port);
            Route r = Route.Instance;
            Thread.Sleep(2000);
            ViewBag.ms = 1000 * time;
            ViewBag.times = int.MaxValue;
            return View();
        }

        [HttpGet]
        public ActionResult display3(string ip, int port, int time, int forTime, string fileName)
        {
            Route.Instance.Clear();
            Connect c = Connect.Instance.connect(ip, port);
            FileHandler.Instance.FileName = Server.MapPath(@"~/" + fileName + ".txt");
            Route r = Route.Instance;
            Thread.Sleep(2000);
            ViewBag.ms = 1000 * time;
            ViewBag.times = forTime * 1000;
            ViewBag.fileName = fileName;
            return View();
        }

        [HttpGet]
        public JsonResult getRoute()
        {
            Route r = Route.Instance;
            r.addPoint();
            IList<Point> route = r.getRouteList();
            return Json(new { route = route }, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult getRoute2()
        {
            Route r = Route.Instance;
            r.addPoint();
            FileHandler.Instance.addLine("" + Connect.Instance.Lon + "," + Connect.Instance.Lat + "," + Connect.Instance.Throttle + "," + Connect.Instance.Rudder + Environment.NewLine);
            IList<Point> route = r.getRouteList();
            return Json(new { route = route }, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult readRouteFromFile()
        {
            Route r = Route.Instance;
            if (r.addPointFromFile() == null)//incase we finished reading from the file.
            {
                IList<Point> routeDone = new List<Point>
                {
                    new Point(22101995, 59910122)//special coardinates to signal stopping and alerting
                };
                return Json(new { route = routeDone }, JsonRequestBehavior.AllowGet);
            }
            IList<Point> route = r.getRouteList();
            return Json(new { route = route }, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult writeAllToFile()
        {
            FileHandler.Instance.Flush();
            IList<Point> route = new List<Point>();
            return Json(new { route = route }, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public ActionResult MapImage()
        {
            return View();
        }
    }
}