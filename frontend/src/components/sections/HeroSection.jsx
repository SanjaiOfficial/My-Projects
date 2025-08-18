import React from 'react';
import { ArrowDown, Download, Github, Linkedin, Mail, MapPin, Calendar } from 'lucide-react';
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
    <section className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-50 via-blue-50 to-teal-50 pt-20">
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          {/* Content */}
          <div className="space-y-8">
            <div className="space-y-4">
              <Badge variant="secondary" className="bg-blue-100 text-blue-700 hover:bg-blue-200">
                <Calendar size={14} className="mr-1" />
                Available for Opportunities
              </Badge>
              
              <h1 className="text-4xl md:text-6xl font-bold text-slate-800 leading-tight">
                Hi, I'm{' '}
                <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                  {portfolioData.personal.name}
                </span>
              </h1>
              
              <h2 className="text-xl md:text-2xl text-slate-600 font-medium">
                {portfolioData.personal.title}
              </h2>
              
              <p className="text-lg text-slate-700 leading-relaxed max-w-2xl">
                {portfolioData.personal.bio}
              </p>
              
              <div className="flex items-center space-x-2 text-slate-600">
                <MapPin size={18} />
                <span>{portfolioData.personal.location}</span>
              </div>
            </div>

            {/* Tech Stack Preview */}
            <div className="space-y-3">
              <p className="text-sm font-semibold text-slate-700 uppercase tracking-wide">
                Specialized In
              </p>
              <div className="flex flex-wrap gap-2">
                {['Java', 'Spring Boot', 'PostgreSQL', 'React', 'Git'].map((tech) => (
                  <Badge 
                    key={tech} 
                    variant="outline" 
                    className="border-blue-200 text-blue-700 hover:bg-blue-50"
                  >
                    {tech}
                  </Badge>
                ))}
              </div>
            </div>

            {/* CTA Buttons */}
            <div className="flex flex-col sm:flex-row gap-4">
              <Button 
                onClick={scrollToAbout}
                size="lg"
                className="bg-blue-600 hover:bg-blue-700 text-white"
              >
                View My Work
                <ArrowDown size={18} className="ml-2" />
              </Button>
              
              <Button 
                onClick={downloadResume}
                size="lg"
                variant="outline"
                className="border-slate-300 hover:bg-slate-50"
              >
                <Download size={18} className="mr-2" />
                Download Resume
              </Button>
            </div>

            {/* Social Links */}
            <div className="flex items-center space-x-6">
              <a 
                href={portfolioData.social.github}
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center space-x-2 text-slate-600 hover:text-blue-600 transition-colors"
              >
                <Github size={20} />
                <span className="hidden sm:inline">GitHub</span>
              </a>
              
              <a 
                href={portfolioData.social.linkedin}
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center space-x-2 text-slate-600 hover:text-blue-600 transition-colors"
              >
                <Linkedin size={20} />
                <span className="hidden sm:inline">LinkedIn</span>
              </a>
              
              <a 
                href={`mailto:${portfolioData.social.email}`}
                className="flex items-center space-x-2 text-slate-600 hover:text-blue-600 transition-colors"
              >
                <Mail size={20} />
                <span className="hidden sm:inline">Email</span>
              </a>
            </div>
          </div>

          {/* Profile Image */}
          <div className="flex justify-center lg:justify-end">
            <div className="relative">
              {/* Background decoration */}
              <div className="absolute inset-0 bg-gradient-to-r from-blue-400 to-teal-400 rounded-3xl transform rotate-6"></div>
              <div className="absolute inset-0 bg-gradient-to-r from-blue-500 to-teal-500 rounded-3xl transform rotate-3"></div>
              
              {/* Main image */}
              <div className="relative bg-white p-2 rounded-3xl shadow-xl">
                <img
                  src={portfolioData.personal.avatar}
                  alt={portfolioData.personal.name}
                  className="w-80 h-80 object-cover rounded-2xl"
                />
                
                {/* Floating elements */}
                <div className="absolute -top-4 -right-4 bg-white rounded-full p-3 shadow-lg">
                  <div className="w-6 h-6 bg-gradient-to-r from-green-400 to-emerald-400 rounded-full animate-pulse"></div>
                </div>
                
                <div className="absolute -bottom-4 -left-4 bg-white rounded-xl p-3 shadow-lg">
                  <div className="flex items-center space-x-2">
                    <div className="w-3 h-3 bg-blue-500 rounded-full"></div>
                    <span className="text-xs font-semibold text-slate-700">
                      Available
                    </span>
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
            className="animate-bounce p-2 rounded-full bg-white shadow-lg hover:shadow-xl transition-shadow"
          >
            <ArrowDown size={24} className="text-slate-600" />
          </button>
        </div>
      </div>
    </section>
  );
};

export default HeroSection;