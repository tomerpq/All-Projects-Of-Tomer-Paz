using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Excercise_1
{
    public class ComposedMission : IMission
    {
        public event EventHandler<double> OnCalculate;
        private String name; //name of the mission
        private List<Func<double, double>> composedFuncs; //the funcs in queue order to be used as arkava
        public ComposedMission(String name)
        {
            this.name = name;
            composedFuncs = new List<Func<double, double>>();
        }
        public String Name
        {
            get
            {
                return name;
            }
        }
        public String Type
        {
            get
            {
                return "Composed";
            }
        }
        public double Calculate(double value)
        {
            foreach(var func in composedFuncs) //calculating the composed functions by the queue order
            {
                value = func(value);
            }
            OnCalculate?.Invoke(this, value); //the events are now being running
            return value;
        }
        public ComposedMission Add(Func<double,double> func) //add new composed function to the queue
        {
            composedFuncs.Add(new Func<double,double>(func));
            return this;
        }
    }
}
