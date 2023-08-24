import ArtistLandingComponent from "./Artist/ArtistLanding";
import UserLandingComponent from "./User/UserLanding";
import Header from "./Header";
import { Link, Outlet, Route, Routes } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import NotFound from "./NotFound";
import { useState } from "react";

function Landing() {

	var navigate = useNavigate();
	const [selectedRole, setSelectedRole] = useState(null);

	var UserLanding = () => {
		// debugger;
		navigate("/user");
	}
	var ArtistLanding = () => {
		// debugger;
		navigate("/artist");
	}

	const renderComponent = () => {
        if (selectedRole === "user") {
            return <UserLandingComponent />;
        } else if (selectedRole === "artist") {
            return <ArtistLandingComponent />;
        }
        return null;
    };

	return (

		<div className='container'>
			<Header></Header>
			<hr></hr>
			<div style={{ fontSize: "x-large", textAlign: "center" }}>
                <button
                    className='btn waves-effect waves-light #e53935 light-blue darken-1'
                    onClick={() => setSelectedRole("user")}
                >
                    User
                </button>
                |
                <button
                    className='btn waves-effect waves-light'
                    onClick={() => setSelectedRole("artist")}
                >
                    Artist
                </button>
            </div>

            <hr></hr>
            <div>
                {selectedRole === "user" && <UserLandingComponent />}
                {selectedRole === "artist" && <ArtistLandingComponent />}
            </div>

			<hr></hr>
			{/* {renderComponent()} */}
			<Routes>
				<Route path="/" element={<Outlet />}>
					<Route path="/user" element={<UserLandingComponent />} />
					<Route path="/artist" element={<ArtistLandingComponent />} />
					<Route path="*" element={<NotFound />} />
				</Route>
			</Routes>

			{/* <Footer/> */}
		</div>
	);
}

export default Landing;