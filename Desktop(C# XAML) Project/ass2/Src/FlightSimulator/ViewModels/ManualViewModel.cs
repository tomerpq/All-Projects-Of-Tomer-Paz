using FlightSimulator.Model;
using System;

namespace FlightSimulator.ViewModels
{
    internal class ManualViewModel : BaseNotify
    {
        private Commands _commands;
        private double _rudderSliderVal;
        private double _throttleSliderVal;

        public ManualViewModel()
        {
            _commands = Commands.Instance;
            RudderSliderVal = 0.0;
            ThrottleSliderVal = 0.0;
        }

        // in the set we send the command also.
        public double RudderSliderVal
        {
            get => _rudderSliderVal;
            set
            {
                _rudderSliderVal = value;
                NotifyPropertyChanged("RudderSliderVal");
                _commands.sendCommand("set controls/flight/rudder " + Math.Round(_rudderSliderVal, 2));
            }
        }

        public double ThrottleSliderVal
        {
            get => _throttleSliderVal;

            set
            {
                _throttleSliderVal = value;
                NotifyPropertyChanged("ThrottleSliderVal");
                _commands.sendCommand("set controls/engines/current-engine/throttle " + Math.Round(_throttleSliderVal, 2));
            }
        }
    }
}