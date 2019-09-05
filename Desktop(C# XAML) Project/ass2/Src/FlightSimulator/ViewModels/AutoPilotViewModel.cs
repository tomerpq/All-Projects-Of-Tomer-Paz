using FlightSimulator.Model;
using System.Threading.Tasks;
using System.Windows.Input;
using System.Windows.Media;

namespace FlightSimulator.ViewModels
{
    internal class AutoPilotViewModel : BaseNotify
    {
        private string codetext; //the text now on diplayed
        private string oldtext;  //the text that was sent last time
        private Commands _commands;

        public AutoPilotViewModel()
        {
            _commands = Commands.Instance;
            codeText = "";
            oldText = "";
        }

        #region Commands

        #region ClickCommand

        private ICommand _clickCommand;
        public ICommand ClickCommand => _clickCommand ?? (_clickCommand = new CommandHandler(() => OnClick()));

        private void OnClick()
        {
            oldText = codeText;
            string[] commands = codeText.Split('\n');
            //sending commands one by one
            foreach (string command in commands)
            {
                Task.Delay(100); // waiting 100 miliseconds
                _commands.sendCommand(command);
            }
        }

        #endregion ClickCommand

        #region CancelCommand

        private ICommand _cancelCommand;
        public ICommand CancelCommand => _cancelCommand ?? (_cancelCommand = new CommandHandler(() => OnCancel()));

        private void OnCancel()
        {
            //cleans up the textbox
            oldText = "";
            codeText = "";
        }

        #endregion CancelCommand

        #endregion Commands

        public string codeText
        {
            get => codetext;
            set { codetext = value; NotifyPropertyChanged("codeText"); NotifyPropertyChanged("Background"); }
        }

        public string oldText { get => oldtext; set { oldtext = value; NotifyPropertyChanged("Background"); } }

        //sending the correct brush to paint the textbox
        public SolidColorBrush Background => codetext.Equals(oldtext) ? Brushes.Transparent : Brushes.LightPink;
    }
}