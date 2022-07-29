import Login from "./Login"
import FormHeader from "./FormHeader"

export default function LoginPage(){
    return(
        <>
        
        <FormHeader
        heading="Login to your account"
        paragraph="Don't have an account yet? "
        linkName="Signup"
        linkUrl="/signup"
        />
        <div className="flex justify-center sm:px-6 lg:px-8">
            <div className="max-w-md w-full space-y-8">
                <Login/>
            </div>
        </div>
        </>    
    )
}