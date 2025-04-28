import './Footer.css'

export default function Footer(){
    return (
        <div className="footer">
            <footer>
            <nav className="footer-nav">
            <a href="mailto:ramyakata69@gmail.com">📧</a>
                <a href="https://www.instagram.com/">📷</a>
                <a href="https://www.youtube.com/">▶️</a>
                <a href="https://www.linkedin.com/in/ramyakata">🔗</a> </nav>
            <p className="footer-text">&copy; {new Date().getFullYear()} All rights reserved.</p>
            </footer>
        </div>
    );
}