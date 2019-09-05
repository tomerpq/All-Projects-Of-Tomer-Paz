using FlightSimulator.Model.Interface;
using FlightSimulator.ViewModels;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace FlightSimulator.Model
{
    public class Connect
    {
        private ISettingsModel _apsm;

        #region Singleton

        private static Connect m_Instance = null;

        //the server we created to read lon and lat
        private TcpListener listner;

        //the client we created to send the commands from
        private Commands commands;

        private TcpClient Info;

        public static Connect Instance
        {
            get
            {
                if (m_Instance == null)
                {
                    m_Instance = new Connect();
                }
                return m_Instance;
            }
        }

        #endregion Singleton

        private Connect()
        {
            //getting the instance from the APSM
            _apsm = ApplicationSettingsModel.Instance;
            listner = new TcpListener(_apsm.FlightInfoPort);
            listner.Start();
            commands = Commands.Instance;
        }

        public ISettingsModel getAPSM()
        {
            return _apsm;
        }

        public void connect()
        {
            commands.Connect(_apsm.FlightServerIP, _apsm.FlightCommandPort);
            //create thread to run the loop
            ThreadStart loopref = new ThreadStart(GetLonAndLat);
            Thread loopThread = new Thread(loopref);
            loopThread.Start();
        }

        public void disconnect()
        {
            try
            {
                //cleaning all resources
                Info.Dispose();
                listner.Stop();
                commands.close();
            }
            catch { }
        }

        private void GetLonAndLat()
        {
            FlightBoardViewModel fvm = FlightBoardViewModel.Instance;
            Info = listner.AcceptTcpClient();                               //getting the info path
            NetworkStream ns = Info.GetStream();                            //networkstream is used to send/receive messages
            while (Info.Connected)                                          //while the client is connected, we look for incoming messages
            {
                byte[] msg = Enumerable.Repeat((byte)0x20, 512).ToArray();  //the messages arrive as byte array
                try
                {
                    ns.Write(msg, 0, msg.Length);                           //write to clear buffer
                    ns.Read(msg, 0, msg.Length);                            //the same networkstream reads the message sent by the client
                }
                catch { }
                string message = Encoding.Default.GetString(msg).TrimEnd(); //now , we write the message as string
                string[] lines = message.Split('\n');
                if (lines.Length == 1)
                {
                    string[] numbers = lines[0].Split(',');
                    if (numbers.Length == 25)
                    {
                        if (double.TryParse(numbers[1], out double temp2))
                        {
                            fvm.Lat = temp2;
                        }

                        if (double.TryParse(numbers[0], out double temp1))
                        {
                            fvm.Lon = temp1;
                        }
                    }
                }
            }
        }
    }
}