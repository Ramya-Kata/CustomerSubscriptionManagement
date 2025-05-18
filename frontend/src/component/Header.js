import '../styles/Header.css'

/**
 * Header Component
 * 
 * Displays a fixed header containing a profile image.
 * Clicking the image navigates to the profile page.
 * This component can be reused across all authenticated pages.
 */

export default function Header(){

  return(  
    <header className="header">
      <div className="header-content">
        <a href="/profile">
          <img src="/OIP.jpg" alt="Profile" className="profile-img" />
        </a>
      </div>
    </header>
  )
}