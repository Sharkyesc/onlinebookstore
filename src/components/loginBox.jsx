export default function loginBox() {
    return (
        <>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>BookStore Login</title>
        <link rel="stylesheet" href="css/loginstyle.css" />
        <div className="login-form">
            <div className="logo">
                <img src="./img/logo.png" alt="logo" />
                <label>Sign in</label>
            </div>
            <div className="login-option">
                <button className="log-options" id="pwdLog" onclick="ways2Login()">
                    Password
                </button>
                <label>|</label>
                <button className="log-options" id="codeLog" onclick="ways2Login()">
                    Code
                </button>
            </div>
            <form id="loginForm">
                <div className="form-group" id="username">
                    <label htmlFor="username">Email / mobile phone number:</label>
                    <input type="text" id="username" name="username" />
                </div>
                <div className="form-group" id="password">
                    <label htmlFor="password">Password:</label>
                    <input type="password" id="password" name="password" />
                </div>
                <div className="form-group" id="code">
                    <label htmlFor="code">Code:</label>
                    <input type="text" id="code" name="code" />
                    <button type="button" id="sendCode">
                        Send Code
                    </button>
                </div>
                <div className="form-group">
                    <input type="submit" defaultValue="Sign in" />
                </div>
                <div className="additional-options">
                    <a href="#">Register now</a> |<a href="#">Forget password</a>
                </div>
            </form>
        </div>
    </>
    );
}
