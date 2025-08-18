import React from 'react';
import { Github, Linkedin, Mail, Heart, Code, Coffee } from 'lucide-react';
import { portfolioData } from '../data/mock';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  const quickLinks = [
    { name: 'About', href: '#about' },
    { name: 'Projects', href: '#projects' },
    { name: 'Experience', href: '#experience' },
    { name: 'Contact', href: '#contact' }
  ];

  const skills = [
    'Java', 'Spring Boot', 'PostgreSQL', 'React', 'Git'
  ];

  const scrollToSection = (href) => {
    const element = document.querySelector(href);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <footer className="bg-slate-900 text-white">
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          {/* About Section */}
          <div className="space-y-4">
            <div className="flex items-center space-x-2">
              <div className="w-8 h-8 bg-gradient-to-r from-blue-500 to-teal-500 rounded-lg flex items-center justify-center">
                <span className="text-white font-bold">S</span>
              </div>
              <h3 className="text-xl font-bold">{portfolioData.personal.name}</h3>
            </div>
            <p className="text-slate-300 text-sm leading-relaxed">
              Computer Science student passionate about backend development with Java & Spring Boot. 
              Building scalable applications and always learning new technologies.
            </p>
            <div className="flex items-center space-x-4">
              <a 
                href={portfolioData.social.github}
                target="_blank"
                rel="noopener noreferrer"
                className="text-slate-400 hover:text-blue-400 transition-colors"
              >
                <Github size={20} />
              </a>
              <a 
                href={portfolioData.social.linkedin}
                target="_blank"
                rel="noopener noreferrer"
                className="text-slate-400 hover:text-blue-400 transition-colors"
              >
                <Linkedin size={20} />
              </a>
              <a 
                href={`mailto:${portfolioData.social.email}`}
                className="text-slate-400 hover:text-blue-400 transition-colors"
              >
                <Mail size={20} />
              </a>
            </div>
          </div>

          {/* Quick Links */}
          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-white">Quick Links</h3>
            <ul className="space-y-2">
              {quickLinks.map((link) => (
                <li key={link.name}>
                  <button
                    onClick={() => scrollToSection(link.href)}
                    className="text-slate-300 hover:text-blue-400 transition-colors text-sm"
                  >
                    {link.name}
                  </button>
                </li>
              ))}
            </ul>
          </div>

          {/* Technologies */}
          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-white">Technologies</h3>
            <div className="flex flex-wrap gap-2">
              {skills.map((skill) => (
                <span 
                  key={skill}
                  className="px-3 py-1 bg-slate-800 text-slate-300 text-xs rounded-full border border-slate-700"
                >
                  {skill}
                </span>
              ))}
            </div>
          </div>

          {/* Contact Info */}
          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-white">Get In Touch</h3>
            <div className="space-y-2">
              <p className="text-slate-300 text-sm">
                <Mail size={16} className="inline mr-2" />
                {portfolioData.personal.email}
              </p>
              <p className="text-slate-300 text-sm">
                📍 {portfolioData.personal.location}
              </p>
              <p className="text-slate-300 text-sm">
                {portfolioData.contact.availability}
              </p>
            </div>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="border-t border-slate-800 mt-8 pt-8">
          <div className="flex flex-col md:flex-row items-center justify-between space-y-4 md:space-y-0">
            <div className="flex items-center space-x-2 text-slate-400 text-sm">
              <span>© {currentYear} {portfolioData.personal.name}.</span>
              <span>Made with</span>
              <Heart size={14} className="text-red-400 fill-current" />
              <span>and</span>
              <Code size={14} className="text-blue-400" />
              <span>and lots of</span>
              <Coffee size={14} className="text-orange-400" />
            </div>
            <div className="text-slate-400 text-sm">
              Built with React • Spring Boot • PostgreSQL
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;