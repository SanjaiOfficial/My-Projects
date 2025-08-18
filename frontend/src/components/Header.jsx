import React, { useState, useEffect } from 'react';
import { Menu, X, Github, Linkedin, Mail } from 'lucide-react';
import { Button } from './ui/button';
import { portfolioData } from '../data/mock';

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 50);
    };
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const navItems = [
    { name: 'About', href: '#about' },
    { name: 'Skills', href: '#skills' },
    { name: 'Projects', href: '#projects' },
    { name: 'Education', href: '#education' },
    { name: 'Blog', href: '#blog' },
    { name: 'Contact', href: '#contact' }
  ];

  const scrollToSection = (href) => {
    const element = document.querySelector(href);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
    setIsMenuOpen(false);
  };

  return (
    <header 
      className={`fixed top-0 left-0 right-0 z-50 transition-all duration-500 ${
        isScrolled 
          ? 'glass-dark backdrop-blur-xl border-b border-slate-700/50 shadow-2xl' 
          : 'bg-slate-900/80 backdrop-blur-sm'
      }`}
    >
      <div className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          {/* Logo */}
          <div className="flex items-center space-x-3 slide-in">
            <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-teal-400 rounded-lg flex items-center justify-center glow-blue hover-glow-blue transition-all duration-300">
              <span className="text-white font-bold text-xl">S</span>
            </div>
            <div>
              <h1 className="text-xl font-bold text-white">{portfolioData.personal.name}</h1>
              <p className="text-xs text-slate-400 hidden sm:block">CS Developer</p>
            </div>
          </div>

          {/* Desktop Navigation */}
          <nav className="hidden lg:flex items-center space-x-8">
            {navItems.map((item, index) => (
              <button
                key={item.name}
                onClick={() => scrollToSection(item.href)}
                className="text-slate-300 hover:text-cyan-400 transition-all duration-300 font-medium relative group slide-in-delayed"
                style={{ animationDelay: `${index * 0.1}s` }}
              >
                {item.name}
                <span className="absolute bottom-0 left-0 w-0 h-0.5 bg-gradient-to-r from-blue-500 to-teal-400 transition-all duration-300 group-hover:w-full"></span>
              </button>
            ))}
          </nav>

          {/* Social Links & CTA */}
          <div className="hidden lg:flex items-center space-x-4">
            <a 
              href={portfolioData.social.github}
              target="_blank"
              rel="noopener noreferrer"
              className="text-slate-300 hover:text-cyan-400 transition-all duration-300 hover:scale-110"
            >
              <Github size={20} />
            </a>
            <a 
              href={portfolioData.social.linkedin}
              target="_blank"
              rel="noopener noreferrer"
              className="text-slate-300 hover:text-cyan-400 transition-all duration-300 hover:scale-110"
            >
              <Linkedin size={20} />
            </a>
            <Button 
              onClick={() => scrollToSection('#contact')}
              className="bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white border-0 hover-glow-blue transition-all duration-300"
            >
              <Mail size={16} className="mr-2" />
              Contact Me
            </Button>
          </div>

          {/* Mobile Menu Button */}
          <button
            onClick={() => setIsMenuOpen(!isMenuOpen)}
            className="lg:hidden text-slate-300 hover:text-cyan-400 transition-all duration-300"
          >
            {isMenuOpen ? <X size={24} /> : <Menu size={24} />}
          </button>
        </div>

        {/* Mobile Navigation */}
        {isMenuOpen && (
          <div className="lg:hidden mt-4 py-4 border-t border-slate-700/50 glass-dark rounded-lg mt-4">
            <nav className="flex flex-col space-y-4 px-4">
              {navItems.map((item) => (
                <button
                  key={item.name}
                  onClick={() => scrollToSection(item.href)}
                  className="text-slate-300 hover:text-cyan-400 transition-all duration-300 font-medium text-left"
                >
                  {item.name}
                </button>
              ))}
              <div className="flex items-center space-x-4 pt-4 border-t border-slate-700/50">
                <a 
                  href={portfolioData.social.github}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-slate-300 hover:text-cyan-400 transition-all duration-300"
                >
                  <Github size={20} />
                </a>
                <a 
                  href={portfolioData.social.linkedin}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-slate-300 hover:text-cyan-400 transition-all duration-300"
                >
                  <Linkedin size={20} />
                </a>
                <Button 
                  onClick={() => scrollToSection('#contact')}
                  className="bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white border-0"
                >
                  <Mail size={16} className="mr-2" />
                  Contact Me
                </Button>
              </div>
            </nav>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;