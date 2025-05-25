import React from "react"

import AuthorizationForm from "../components/UI/Forms/AuthorizationFormC"
import RegistrationForm from "../components/UI/Forms/RegistrationFormC"


interface AuthorizationViewProps{
	onLoginP : () => void
}

const AuthorizationPage: React.FC<AuthorizationViewProps> = 
	({
		onLoginP
	}) => {
	const [authorizing, setAuthorizing] = React.useState<boolean>(true);

	const changeAuthorizing = () =>{
		setAuthorizing(!authorizing);
	}

	return (
		<div className="AuthorizationViewContainer">
			<div className="AuthorizationViewContentHandler">
				{authorizing == true ? 
				<AuthorizationForm onLoginClickP={onLoginP} onRegisterClickP={changeAuthorizing}/> : 
				<RegistrationForm onLoginClickP={changeAuthorizing} onRegisterClickP={onLoginP}/>
				}
			</div>
		</div>)

}

export default AuthorizationPage
