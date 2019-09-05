using FlightSimulator.Model;
using System;

namespace FlightSimulator.ViewModels
{
    internal class JoystickViewModel
    {
        private Commands _commands;

        public JoystickViewModel()
        {
            _commands = Commands.Instance;
        }

        public void propertyChanged(string property, double value)
        {
            //sending the correct command. 
            if (property.Equals("Aileron"))
            {
                _commands.sendCommand("set controls/flight/aileron " + +Math.Round(value, 2));
            }
            else if (property.Equals("Elevator"))
            {
                _commands.sendCommand("set controls/flight/elevator " + +Math.Round(value, 2));
            }
        }
    }
}