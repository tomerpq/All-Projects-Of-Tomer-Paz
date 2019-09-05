using System;
using System.Drawing;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Ex3.Models
{
    public class Connect
    {
        private TcpClient client;

        private double lat;
        private double lon;
        private double throttle;
        private double rudder;
        private Thread loopThread;

        #region singelton

        private static readonly Connect m_Instance = new Connect();

        public static Connect Instance => m_Instance;

        public double Lat { get => lat; set => lat = value; }
        public double Lon { get => lon; set => lon = value; }
        public double Throttle { get => throttle; set => throttle = value; }
        public double Rudder { get => rudder; set => rudder = value; }

        private Connect()
        {
            client = new TcpClient();
        }

        #endregion singelton

        public void close()
        {
            if (loopThread != null && loopThread.IsAlive == true)
            {
                loopThread.Abort();
            }
            if (client != null)
            {
                client.Dispose();
            }
        }

        public Point getLocation()
        {
            //Debug.WriteLine("lon: " + Lon + " lat: " + Lat + " throttle: " + Throttle + " rudder: " + Rudder);
            return new Point(Math.Abs((int)Lon) % 4160, Math.Abs((int)Lat) % 2164);
        }

        public Connect connect(string FlightServerIP, int FlightCommandsPort)
        {
            FileHandler.Instance.Clear();
            close();
            //connecting to client, when connecting we actually create new connect instance
            client = new TcpClient(FlightServerIP, FlightCommandsPort);
            //create thread to run the loop
            ThreadStart loopref = new ThreadStart(GetLonAndLat);
            loopThread = new Thread(loopref);
            loopThread.Start();

            return this;
        }

        private void GetLonAndLat()
        {
            NetworkStream ns = client.GetStream();                            //networkstream is used to send/receive messages
            while (true)                                          //while the client is connected, we look for incoming messages
            {
                try
                {
                    getLat();
                    getLon();
                    getThrottle();
                    getRudder();
                }
                catch
                {
                }
            }
        }

        private void getLat()
        {
            byte[] data = new byte[1024];

            // Get a client stream for reading and writing.
            NetworkStream stream = client.GetStream();

            string getCommand = "get position/latitude-deg" + "\n\r\n\r";

            if (stream.CanWrite)
            {
                stream.Write(Encoding.ASCII.GetBytes(getCommand), 0, getCommand.Length);
                stream.Flush();
            }

            //getting from the server the answer
            int receivedDataLength = stream.Read(data, 0, data.Length);
            string stringData = Encoding.ASCII.GetString(data, 0, receivedDataLength);

            string[] parts = stringData.Split(' ');
            string lastWord = parts[parts.Length - 3];
            lastWord = lastWord.TrimEnd('\'');
            lastWord = lastWord.TrimStart('\'');

            Lat = Convert.ToDouble(lastWord);
        }

        private void getLon()
        {
            byte[] data = new byte[1024];

            // Get a client stream for reading and writing.
            NetworkStream stream = client.GetStream();

            string getCommand = "get position/longitude-deg" + "\n\r\n\r";

            if (stream.CanWrite)
            {
                stream.Write(Encoding.ASCII.GetBytes(getCommand), 0, getCommand.Length);
                stream.Flush();
            }

            //getting from the server the answer
            int receivedDataLength = stream.Read(data, 0, data.Length);
            string stringData = Encoding.ASCII.GetString(data, 0, receivedDataLength);

            string[] parts = stringData.Split(' ');
            string lastWord = parts[parts.Length - 3];
            lastWord = lastWord.TrimEnd('\'');
            lastWord = lastWord.TrimStart('\'');

            Lon = Convert.ToDouble(lastWord);
        }

        private void getThrottle()
        {
            byte[] data = new byte[1024];

            // Get a client stream for reading and writing.
            NetworkStream stream = client.GetStream();

            string getCommand = "get controls/engines/current-engine/throttle" + "\n\r\n\r";

            if (stream.CanWrite)
            {
                stream.Write(Encoding.ASCII.GetBytes(getCommand), 0, getCommand.Length);
                stream.Flush();
            }

            //getting from the server the answer
            int receivedDataLength = stream.Read(data, 0, data.Length);
            string stringData = Encoding.ASCII.GetString(data, 0, receivedDataLength);

            string[] parts = stringData.Split(' ');
            string lastWord = parts[parts.Length - 3];
            lastWord = lastWord.TrimEnd('\'');
            lastWord = lastWord.TrimStart('\'');

            Throttle = Convert.ToDouble(lastWord);
        }

        private void getRudder()
        {
            byte[] data = new byte[1024];

            // Get a client stream for reading and writing.
            NetworkStream stream = client.GetStream();

            string getCommand = "get controls/flight/rudder" + "\n\r\n\r";

            if (stream.CanWrite)
            {
                stream.Write(Encoding.ASCII.GetBytes(getCommand), 0, getCommand.Length);
                stream.Flush();
            }

            //getting from the server the answer
            int receivedDataLength = stream.Read(data, 0, data.Length);
            string stringData = Encoding.ASCII.GetString(data, 0, receivedDataLength);

            string[] parts = stringData.Split(' ');
            string lastWord = parts[parts.Length - 3];
            lastWord = lastWord.TrimEnd('\'');
            lastWord = lastWord.TrimStart('\'');

            Rudder = Convert.ToDouble(lastWord);
        }
    }
}