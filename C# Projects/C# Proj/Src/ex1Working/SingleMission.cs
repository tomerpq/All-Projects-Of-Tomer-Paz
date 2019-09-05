using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Excercise_1
{
    public class SingleMission : IMission
    {
        public event EventHandler<double> OnCalculate;
        private String name; //name of the mission
        private Func<double, double> func; //the single function we will use to calculate
        public SingleMission(Func<double,double> func,String name)
        {
            this.name = name;
            this.func = new Func<double,double>(func);
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
                return "Single";
            }
        }
        public double Calculate(double value)
        {
            double rtr = func(value); //calculate by the single function we have
            OnCalculate?.Invoke(this, rtr); //the events are now being running
            return rtr;
        }

    }
}
