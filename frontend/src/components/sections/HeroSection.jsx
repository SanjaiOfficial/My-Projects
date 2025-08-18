import React from 'react';
import { ArrowDown, Download, Github, Linkedin, Mail, MapPin, Calendar, Sparkles } from 'lucide-react';
import { Button } from '../ui/button';
import { Badge } from '../ui/badge';
import { portfolioData } from '../../data/mock';

const HeroSection = () => {
  const scrollToAbout = () => {
    document.querySelector('#about')?.scrollIntoView({ behavior: 'smooth' });
  };

  const downloadResume = () => {
    // Mock download functionality
    const link = document.createElement('a');
    link.href = portfolioData.personal.resume;
    link.download = 'Sanjai_CS_Developer_Resume.pdf';
    link.click();
  };

  return (
    <section className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 pt-20 relative overflow-hidden">
      {/* Animated background elements */}
      <div className="absolute inset-0">
        <div className="absolute top-20 left-20 w-72 h-72 bg-blue-500/10 rounded-full blur-3xl animate-pulse"></div>
        <div className="absolute bottom-20 right-20 w-96 h-96 bg-teal-500/10 rounded-full blur-3xl animate-pulse" style={{animationDelay: '1s'}}></div>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-64 h-64 bg-purple-500/5 rounded-full blur-3xl animate-pulse" style={{animationDelay: '2s'}}></div>
      </div>

      <div className="container mx-auto px-4 py-12 relative z-10">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          {/* Content */}
          <div className="space-y-8 slide-in">
            <div className="space-y-6">
              <Badge variant="secondary" className="bg-gradient-to-r from-green-900/50 to-emerald-900/50 text-green-400 border border-green-500/30 hover:glow-green pulse-glow">
                <Calendar size={14} className="mr-2" />
                <Sparkles size={14} className="mr-1" />
                Available for Opportunities
              </Badge>
              
              <h1 className="text-4xl md:text-6xl font-bold leading-tight">
                <span className="text-white">Hi, I'm </span>
                <span className="neon-text bg-gradient-to-r from-cyan-400 via-blue-500 to-teal-400 bg-clip-text text-transparent">
                  {portfolioData.personal.name}
                </span>
              </h1>
              
              <h2 className="text-xl md:text-2xl text-slate-300 font-medium">
                {portfolioData.personal.title}
              </h2>
              
              <p className="text-lg text-slate-400 leading-relaxed max-w-2xl">
                {portfolioData.personal.bio}
              </p>
              
              <div className="flex items-center space-x-2 text-slate-400">
                <MapPin size={18} className="text-teal-400" />
                <span>{portfolioData.personal.location}</span>
              </div>
            </div>

            {/* Tech Stack Preview */}
            <div className="space-y-4 slide-in-delayed">
              <p className="text-sm font-semibold text-slate-300 uppercase tracking-wider">
                Specialized In
              </p>
              <div className="flex flex-wrap gap-3">
                {['Java', 'Spring Boot', 'PostgreSQL', 'React', 'Git'].map((tech, index) => (
                  <Badge 
                    key={tech} 
                    variant="outline" 
                    className="border-cyan-400/30 text-cyan-300 bg-cyan-900/20 hover:bg-cyan-800/30 hover:border-cyan-300 hover-glow-teal transition-all duration-300"
                    style={{animationDelay: `${index * 0.1}s`}}
                  >
                    {tech}
                  </Badge>
                ))}
              </div>
            </div>

            {/* CTA Buttons */}
            <div className="flex flex-col sm:flex-row gap-4 slide-in-delayed">
              <Button 
                onClick={scrollToAbout}
                size="lg"
                className="bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white border-0 hover-glow-blue transition-all duration-300"
              >
                View My Work
                <ArrowDown size={18} className="ml-2" />
              </Button>
              
              <Button 
                onClick={downloadResume}
                size="lg"
                variant="outline"
                className="border-slate-600 text-slate-300 hover:bg-slate-800 hover:text-white hover:border-slate-500 transition-all duration-300"
              >
                <Download size={18} className="mr-2" />
                Download Resume
              </Button>
            </div>

            {/* Social Links */}
            <div className="flex items-center space-x-6 slide-in-delayed">
              <a 
                href={portfolioData.social.github}
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center space-x-2 text-slate-400 hover:text-cyan-400 transition-all duration-300 group"
              >
                <Github size={20} className="group-hover:scale-110 transition-transform duration-300" />
                <span className="hidden sm:inline">GitHub</span>
              </a>
              
              <a 
                href={portfolioData.social.linkedin}
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center space-x-2 text-slate-400 hover:text-cyan-400 transition-all duration-300 group"
              >
                <Linkedin size={20} className="group-hover:scale-110 transition-transform duration-300" />
                <span className="hidden sm:inline">LinkedIn</span>
              </a>
              
              <a 
                href={`mailto:${portfolioData.social.email}`}
                className="flex items-center space-x-2 text-slate-400 hover:text-cyan-400 transition-all duration-300 group"
              >
                <Mail size={20} className="group-hover:scale-110 transition-transform duration-300" />
                <span className="hidden sm:inline">Email</span>
              </a>
            </div>
          </div>

          {/* Profile Image */}
          <div className="flex justify-center lg:justify-end slide-in-delayed">
            <div className="relative float">
              {/* Glowing background rings */}
              <div className="absolute inset-0 bg-gradient-to-r from-blue-500 to-teal-400 rounded-full blur-xl opacity-20 animate-pulse"></div>
              <div className="absolute inset-0 bg-gradient-to-r from-purple-500 to-cyan-400 rounded-full blur-2xl opacity-10 animate-pulse" style={{animationDelay: '1s'}}></div>
              
              {/* Main image container */}
              <div className="relative gradient-border p-1">
                <div className="relative bg-slate-800 rounded-3xl overflow-hidden">
                  <img
                    src={portfolioData.personal.avatar}
                    alt={portfolioData.personal.name}
                    className="w-80 h-80 object-cover rounded-2xl"
                  />
                  
                  {/* Floating status indicator */}
                  <div className="absolute -top-4 -right-4 bg-gradient-to-r from-green-500 to-emerald-400 rounded-full p-3 glow-green pulse-glow">
                    <div className="w-6 h-6 bg-white rounded-full flex items-center justify-center">
                      <div className="w-3 h-3 bg-green-500 rounded-full animate-ping"></div>
                    </div>
                  </div>
                  
                  {/* Floating availability badge */}
                  <div className="absolute -bottom-6 -left-6 glass-dark rounded-xl p-4 border border-slate-600/50 hover-glow-blue">
                    <div className="flex items-center space-x-2">
                      <div className="w-3 h-3 bg-green-400 rounded-full animate-pulse"></div>
                      <span className="text-sm font-semibold text-slate-200">
                        Available
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Scroll Indicator */}
        <div className="flex justify-center mt-16">
          <button
            onClick={scrollToAbout}
            className="animate-bounce p-3 rounded-full glass-dark hover-glow-blue transition-all duration-300 border border-slate-600/50"
          >
            <ArrowDown size={24} className="text-slate-400" />
          </button>
        </div>
      </div>
    </section>
  );
};

export default HeroSection;