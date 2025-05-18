import '../styles/Footer.css'

/**
 * Footer Component
 * 
 * Displays social/contact icons and copyright text.
 * Can be reused across pages to maintain consistent site branding.
 */
export default function Footer(){
    return (
        <div className="footer">
            <footer>
            <nav className="footer-nav">
            <a href="mailto:ramyakata69@gmail.com">📧</a>
                <a href="https://www.instagram.com/">📷</a>
                <a href="https://www.youtube.com/">▶️</a>
                <a href="www.linkedin.com/in/rkata">🔗</a> </nav>
            <p className="footer-text">&copy; {new Date().getFullYear()} All rights reserved.</p>
            </footer>
        </div>
    );
}