// import Dashboard from "./FunctionalDashboardCRUDandXHR";
import { Route } from "react-router-dom";
import Login from "./User/UserLogin";
import '../../node_modules/bootstrap/dist/css/bootstrap.min.css'


function ProtectedRoute(props) {

    var userIsLoggedIn = window.sessionStorage.getItem("userIsLoggedIn");
    if(userIsLoggedIn!=null && userIsLoggedIn!="undefined" && userIsLoggedIn=="true")
    {
        return(<Route exact path={props.path} component={props.component} 
            userIsLoggedInLanding={props.userIsLoggedInLanding} 
            changeUserIsLoggedInLanding={props.changeUserIsLoggedInLanding}></Route>)
    }
    else
    {
        return(<Login userIsLoggedInLanding={props.userIsLoggedInLanding}
            changeUserIsLoggedInLanding={props.changeUserIsLoggedInLanding}/>)
    }
}

export default ProtectedRoute;