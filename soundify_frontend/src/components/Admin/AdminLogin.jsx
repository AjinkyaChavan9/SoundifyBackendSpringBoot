import { useState } from "react";
import "../../../node_modules/materialize-css/dist/css/materialize.min.css"
import "materialize-css"
//import '../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import axios from 'axios';

import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

function Login(props) {
    const [credentials, setCredentials] = useState({ email: "", password: "" });
    const [message, setMessage] = useState("");

    var navigate = useNavigate();

    var OnTextChange = (args) => {
        var copyOfCredentials = { ...credentials }
        copyOfCredentials[args.target.name] = args.target.value;
        setCredentials(copyOfCredentials);

    }

    var ShowMessage = (message) => {
        setMessage(message);
        setTimeout(() => { setMessage("") }, 3000)

    }

    // var SignIn = () => {
    //     // debugger;
    //     var helper = new XMLHttpRequest();
    //     helper.onreadystatechange = () => {

    //         if (helper.readyState == 4 && helper.status == 200) {
    //             debugger;
    //             var result = JSON.parse(helper.responseText)
    //             console.log(result);

    //             if (result.status == 'success') {
    //                 window.sessionStorage.setItem("userIsLoggedIn", "true");
    //                 props.changeUserIsLoggedInLanding();
    //                 window.sessionStorage.setItem("email", credentials.email);
    //                 window.sessionStorage.setItem("id", result.data[0].id);

    //                 history.push("/dashboard");

    //             }
    //             else {
    //                 ShowMessage("Credentials Invalid")
    //             }
    //         }
    //     }
    //     helper.open("POST", "http://127.0.0.1:8080/api/user/login");
    //     helper.setRequestHeader("content-type", "application/json")
    //     helper.send(JSON.stringify(credentials))


    // }
    const SignIn = () => {
        axios
            .post("http://localhost:8080/api/users/signin", credentials, {
                headers: { "content-type": "application/json" },
            })
            .then((response) => {
                const result = response.data;
                console.log(result);

                if (result.status == "success") {
                    window.sessionStorage.setItem("userIsLoggedIn", "true");
                    props.changeUserIsLoggedInLanding();
                    window.sessionStorage.setItem("email", credentials.email);
                    window.sessionStorage.setItem("id", result.responseObj.id);
                    console.log(result.responseObj.id);

                    navigate("/admindashboard");
                } else {
                    ShowMessage("Credentials Invalid");
                }
            })
            .catch((error) => {
                console.log(error);
                console.error("An error occurred:", error);
                ShowMessage("An error occurred while logging in");
            });
    };


    // var SignUp = () => {
    //     navigate("/register")
    // }


    return (
        <div className="container">
            <form className="col s4">

                <div className="row">
                    <div className="input-field col s4">
                        <input id="email" name="email" type="email" className="validate"
                            value={credentials.email}
                            onChange={OnTextChange} />
                        <label htmlFor="email">Email</label>
                    </div>
                </div>
                <div className="row">
                    <div className="input-field col s4">
                        <input id="password" name="password" type="password" className="validate"
                            value={credentials.password}
                            onChange={OnTextChange} />
                        <label htmlFor="password">Password</label>
                    </div>
                </div>
                <div className="row">
                    <center>

                    </center>
                </div>
                <center>
                    <div className="row">
                        <button className="btn waves-effect waves-light" type="button" name="action"
                            onClick={SignIn}>Login
                        </button>
                    </div>
                    <div>
                        {message !== "" && (
                            <div className="card-panel teal lighten-2">
                                {message}
                            </div>
                        )}

                    </div>
                </center>



            </form>
        </div>

    );
}

export default Login;