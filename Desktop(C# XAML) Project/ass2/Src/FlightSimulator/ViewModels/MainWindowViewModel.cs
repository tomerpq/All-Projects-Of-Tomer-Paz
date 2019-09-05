using FlightSimulator.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FlightSimulator.ViewModels
{
    class MainWindowViewModel
    {
        private Connect _connect;
        private ICommand _connectCommand;
        private ICommand _disconnectCommand;
        #region Singleton
        private static MainWindowViewModel m_Instance = null;
        public static MainWindowViewModel Instance
        {
            get
            {
                if (m_Instance == null)
                {
                    m_Instance = new MainWindowViewModel(Connect.Instance);

                }
                return m_Instance;
            }
        }
        #endregion
        #region Commands
        #region ConnectCommand
        public ICommand ConnectCommand
        {
            get
            {
                return _connectCommand ?? (_connectCommand = new CommandHandler(() => OnClick()));
            }
        }
        private void OnClick()
        {
            _connect.connect();
        }
        #endregion
        #region DisconnectCommand
        public ICommand DisconnectCommand
        {
            get
            {
                return _disconnectCommand ?? (_disconnectCommand = new CommandHandler(() => OnCancel()));
            }
        }
        private void OnCancel()
        {
            _connect.disconnect();
        }
        #endregion
        #endregion
        private MainWindowViewModel(Connect con)
        {
            _connect = con;
        }
    }
}
