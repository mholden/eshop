
const env = "LOCAL";
const IP = "10.0.0.181"

export default class BackEndServiceLocations {

    static getLocation(service) {
        switch (service) {
            case "CATALOG_SERVICE":
            case "BASKET_SERVICE":
            case "ORDER_SERVICE":
            case "CONTENT_SERVICE":
                switch (env) {
                    case "LOCAL": 
                        return "http://" + IP + ":8080";
                    case "DEV": 
                        return "http://eshop.hldn.live:8080";
                    default:
                        break;
                }
                break;
            case "NOTIFICATION_SERVICE":
                switch (env) {
                    case "LOCAL": 
                        return "ws://" + IP + ":8080";
                    case "DEV": 
                        return "ws://eshop.hldn.live:8080";
                    default:
                        break;
                }
                break;
            case "IDENTITY_SERVICE":
                switch (env) {
                    case "LOCAL": 
                        return "http://" + IP + ":8090";
                    case "DEV": 
                        return "https://eshop.hldn.live:8543";
                    default:
                        break;
                }
                break;
            default:
                console.error("unknown service",service);
                break;
        }
    }
}