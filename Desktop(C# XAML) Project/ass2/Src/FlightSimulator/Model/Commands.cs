using System.Net.Sockets;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FlightSimulator.Model
{
    internal class Commands
    {
        private TcpClient client;

        #region singelton

        private static Commands m_Instance;

        public static Commands Instance
        {
            get
            {
                if (m_Instance == null)
                {
                    m_Instance = new Commands();
                }
                return m_Instance;
            }
        }

        private Commands()
        {
            client = new TcpClient();
        }

        #endregion singelton

        public void Connect(string FlightServerIP, int FlightCommandsPort)
        {
            //connecting to client
            client = new TcpClient(FlightServerIP, FlightCommandsPort);
        }

        public void close()
        {
            client.Close();
        }

        public void sendCommand(string Command)
        {
            //if we havnt connected yet we dont send nothing
            if (!client.Connected)
            {
                return;
            }

            Command = Command + "\n\r\n\r";
            // Translate the passed message into ASCII and store it as a Byte array.
            byte[] data = System.Text.Encoding.ASCII.GetBytes(Command);

            // Get a client stream for reading and writing.
            NetworkStream stream = client.GetStream();

            // Send the message to the connected TcpServer.
            stream.Write(data, 0, data.Length);
        }
    }
}