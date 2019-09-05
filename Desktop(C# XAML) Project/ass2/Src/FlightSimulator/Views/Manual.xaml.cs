using FlightSimulator.Model;
using FlightSimulator.ViewModels;
using System.Windows.Controls;

namespace FlightSimulator.Views
{
    /// <summary>
    /// Interaction logic for Manualj.xaml
    /// </summary>
    public partial class Manual : UserControl
    {
        private ManualViewModel vm;

        public Manual()
        {
            InitializeComponent();
            Commands c = Commands.Instance;
            vm = new ManualViewModel();
            DataContext = vm;
        }
    }
}