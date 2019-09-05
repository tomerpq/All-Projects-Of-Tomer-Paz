using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace FlightSimulator.Model
{
    public class CommandHandler : ICommand
    {
        //2 actions can be sent
        private Action _action;
        private Action _action2;

        public CommandHandler(Action action)
        {
            _action = action;
        }
        public CommandHandler(Action action,Action action2)
        {
            _action = action;
            _action2 = action2;
        }
        public bool CanExecute(object parameter)
        {
            return true;
        }

        public event EventHandler CanExecuteChanged;

        public void Execute(object parameter)
        {
            _action();
        }
        public void Execute2(object parameter)
        {
            _action();
            _action2();
        }
    }
}
