import no from './03ed9b172565177.64a53c6c10285.gif'
import './NotFound.css'
export default function NotFound(){
    return (
        <div className="not-found">
        <img src={no} alt="Page Not Found" className="image" />
        <h1 className="title">Oops... You seem to be lost!</h1>
        <p className="text">The page you’re looking for doesn’t exist or has been moved.</p>
        <a href="/" className="back-link">Go Back to Home</a>
    </div>
    );
}