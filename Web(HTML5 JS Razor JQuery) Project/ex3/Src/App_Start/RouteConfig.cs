using System.Web.Mvc;
using System.Web.Routing;

namespace Ex3
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute("display1Or4", "display/{ipOrFileName}/{portOrTime}",
            defaults: new { controller = "Flight", action = "display1Or4" });

            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Flight", action = "Index", id = UrlParameter.Optional }
            );

            routes.MapRoute("display2", "display/{ip}/{port}/{time}",
            defaults: new { controller = "Flight", action = "display2" });

            routes.MapRoute("display3", "display/{ip}/{port}/{time}/{forTime}/{fileName}",
            defaults: new { controller = "Flight", action = "display3" });
        }
    }
}