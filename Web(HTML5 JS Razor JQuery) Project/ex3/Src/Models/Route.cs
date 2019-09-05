using System;
using System.Collections.Generic;
using System.Drawing;

namespace Ex3.Models
{
    public class Route
    {
        private IList<Point> points;
        private int gotFromFile = 0;
        private string fileData = "";

        #region singelton

        private static readonly Route m_Instance = new Route();

        public static Route Instance => m_Instance;

        private Route()
        {
            points = new List<Point>();
            fileData = "";
            gotFromFile = 0;
        }

        #endregion singelton

        public Route addPoint()
        {
            points.Add(Connect.Instance.getLocation());
            return this;
        }

        public Route addPointFromFile()
        {
            double lon = 0.0, lat = 0.0;
            if (gotFromFile == 0)
            {
                //getting data from file(lons,lats):
                fileData = FileHandler.Instance.getLocations();
                gotFromFile = 1;
            }
            if (fileData.Length == 0)//after read its empty file or maybe we emptyed the string after reading from it.
            {
                return null;
            }
            //getting next lon:
            for (int i = 0; i < fileData.Length; i++)
            {
                if (fileData[i] == '-')
                {
                    continue;
                }

                if (fileData[i] == ',')
                {
                    string tmp;
                    if (fileData[0] == '-')
                    {
                        tmp = fileData.Substring(1, i - 1);
                    }
                    else
                    {
                        tmp = fileData.Substring(0, i);
                    }
                    lon = Convert.ToDouble(tmp);
                    fileData = fileData.Substring(i + 1);
                    break;
                }
            }
            //getting next lat:
            for (int i = 0; i < fileData.Length; i++)
            {
                if (fileData[i] == '-')
                {
                    continue;
                }

                if (fileData[i] == ',')
                {
                    string tmp;
                    if (fileData[0] == '-')
                    {
                        tmp = fileData.Substring(1, i - 1);
                    }
                    else
                    {
                        tmp = fileData.Substring(0, i);
                    }
                    lat = Convert.ToDouble(tmp);
                    fileData = fileData.Substring(i + 1);
                    break;
                }
            }
            points.Add(new Point(Math.Abs((int)lon) % 4160, Math.Abs((int)lat) % 2164));
            return this;
        }

        public void Clear()
        {
            points = new List<Point>();
            fileData = "";
            gotFromFile = 0;
        }

        public IList<Point> getRouteList()
        {
            return points;
        }
    }
}