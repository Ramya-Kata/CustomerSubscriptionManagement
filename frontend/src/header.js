import './Header.css'

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