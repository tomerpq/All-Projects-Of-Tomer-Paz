using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Excercise_1
{
    
    public class FunctionsContainer
    {
        private Dictionary<String, Func<double,double>> funcs; //the mapping from the name of the function to the function itself
        public FunctionsContainer()
        {
            funcs = new Dictionary<string, Func<double, double>>();
        }
        public Func<double,double> this[String key]
        {
            get
            {
                if (!funcs.ContainsKey(key)) //no function by this key name - we will add ID function in its name and return it ofcourse
                {
                    funcs.Add(key, x => x);
                }
                return funcs[key];
            }
            set
            {
                if (funcs.ContainsKey(key)) //incase there is a function with that key name replace it by the new function value
                {
                    funcs[key] = value; 
                }
                else
                {
                    funcs.Add(key, value); //add the new function
                }
            }
        }
        public List<String> getAllMissions() 
        {
            return funcs.Keys.ToList(); //get the names of the fucntions we have by the order we added them(queue order)
        }
    }
}
