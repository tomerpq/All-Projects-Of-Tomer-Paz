using System.IO;
using System.Text;

namespace Ex3.Models
{
    public class FileHandler
    {
        private string fileName;
        private StringBuilder sb;

        #region singelton

        private static readonly FileHandler m_Instance = new FileHandler();

        public static FileHandler Instance => m_Instance;

        public string FileName
        {
            get => fileName;
            set
            {
                fileName = value;
                sb = new StringBuilder();
            }
        }

        private FileHandler()
        {
            fileName = "Flight2";
        }

        #endregion singelton

        public FileHandler addLine(string Line)
        {
            sb.Append(Line);
            return this;
        }

        public void Clear()
        {
            sb = new StringBuilder();
        }

        public string getLocations()
        {
            string rtr = "";
            // Open the file to read from.
            using (StreamReader sr = File.OpenText(FileName))
            {
                string s = "";
                while ((s = sr.ReadLine()).Split(',').Length > 2)
                {
                    for (int i = 0; i < s.Length; i++)
                    {//appending lon to rtr:
                        if (s[i] == ',')
                        {
                            string tmp = s.Substring(0, i);
                            rtr = rtr + tmp;
                            s = s.Substring(i + 1);
                            break;
                        }
                    }
                    rtr = rtr + ",";
                    for (int i = 0; i < s.Length; i++)
                    {//appending lat to rtr:
                        if (s[i] == ',')
                        {
                            string tmp = s.Substring(0, i);
                            rtr = rtr + tmp;
                            s = s.Substring(i + 1);
                            break;
                        }
                    }
                    rtr = rtr + ",";
                }
            }
            return rtr;
        }

        public void Flush()
        {
            using (StreamWriter sw = File.CreateText(fileName))
            {
                sw.WriteLine(sb);
            }
        }
    }
}