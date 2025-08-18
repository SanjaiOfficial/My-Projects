import React from 'react';
import { Award, Target, Users, Zap } from 'lucide-react';
import { Card, CardContent } from '../ui/card';
import { Badge } from '../ui/badge';
import { portfolioData } from '../../data/mock';

const AboutSection = () => {
  const stats = [
    { icon: Award, label: 'Projects Completed', value: '15+', color: 'blue' },
    { icon: Target, label: 'Technologies Mastered', value: '12+', color: 'teal' },
    { icon: Users, label: 'Team Collaborations', value: '8+', color: 'purple' },
    { icon: Zap, label: 'Years Learning', value: '3+', color: 'orange' }
  ];

  const highlights = [
    {
      title: "Backend Specialist",
      description: "Expert in Java and Spring Boot development with focus on building scalable REST APIs and microservices architecture.",
      icon: '⚡'
    },
    {
      title: "Database Management",
      description: "Proficient in PostgreSQL database design, optimization, and management with experience in complex queries and performance tuning.",
      icon: '🗄️'
    },
    {
      title: "Full-Stack Capable",
      description: "Strong frontend skills in React and modern JavaScript, enabling end-to-end application development and seamless integration.",
      icon: '🚀'
    },
    {
      title: "Version Control & Collaboration",
      description: "Experienced in Git workflows, code reviews, and agile development practices with strong team collaboration skills.",
      icon: '🔧'
    }
  ];

  return (
    <section id="about" className="py-20 bg-gradient-to-b from-slate-900 to-slate-800 relative overflow-hidden">
      {/* Background effects */}
      <div className="absolute inset-0">
        <div className="absolute top-20 right-20 w-64 h-64 bg-blue-500/5 rounded-full blur-3xl"></div>
        <div className="absolute bottom-20 left-20 w-80 h-80 bg-teal-500/5 rounded-full blur-3xl"></div>
      </div>

      <div className="container mx-auto px-4 relative z-10">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16 slide-in">
            <Badge variant="secondary" className="mb-6 bg-gradient-to-r from-blue-900/50 to-teal-900/50 text-blue-400 border border-blue-500/30 hover:glow-blue">
              About Me
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-white mb-6">
              Passionate About Building{' '}
              <span className="neon-text bg-gradient-to-r from-cyan-400 via-blue-500 to-teal-400 bg-clip-text text-transparent">
                Robust Solutions
              </span>
            </h2>
            <p className="text-lg text-slate-300 max-w-3xl mx-auto leading-relaxed">
              I'm a Computer Science student with a deep passion for backend development and 
              database management. My journey in software development has been driven by curiosity 
              and a commitment to writing clean, efficient code that solves real-world problems.
            </p>
          </div>

          {/* Stats Grid */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-16">
            {stats.map((stat, index) => (
              <Card 
                key={index} 
                className={`text-center p-6 glass-dark border-slate-700/50 hover-glow-${stat.color} transition-all duration-500 slide-in-delayed`}
                style={{ animationDelay: `${index * 0.1}s` }}
              >
                <CardContent className="p-0">
                  <div className="mb-4">
                    <div className={`w-12 h-12 bg-gradient-to-r from-${stat.color}-500/20 to-${stat.color}-400/20 rounded-lg flex items-center justify-center mx-auto border border-${stat.color}-500/30`}>
                      <stat.icon size={24} className={`text-${stat.color}-400`} />
                    </div>
                  </div>
                  <div className="text-2xl font-bold text-white mb-2 neon-text">
                    {stat.value}
                  </div>
                  <div className="text-sm text-slate-400 font-medium">
                    {stat.label}
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>

          {/* Detailed About */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center mb-16">
            {/* Image */}
            <div className="order-2 lg:order-1 slide-in-delayed">
              <div className="relative">
                <div className="absolute inset-0 bg-gradient-to-r from-blue-500/20 to-teal-500/20 rounded-2xl blur-xl"></div>
                <img
                  src="https://images.unsplash.com/photo-1571171637578-41bc2dd41cd2?w=600&h=400&fit=crop"
                  alt="Developer workspace"
                  className="rounded-2xl w-full h-80 object-cover border border-slate-700/50 hover-glow-blue transition-all duration-500 relative z-10"
                />
              </div>
            </div>

            {/* Content */}
            <div className="order-1 lg:order-2 space-y-6 slide-in">
              <div>
                <h3 className="text-2xl font-bold text-white mb-4">
                  My Development Journey
                </h3>
                <p className="text-slate-300 leading-relaxed mb-6">
                  Starting my computer science journey in 2022, I quickly developed a passion 
                  for backend development and database systems. What began as curiosity about 
                  how applications work has evolved into expertise in building scalable, 
                  efficient systems using modern technologies.
                </p>
                <p className="text-slate-300 leading-relaxed">
                  I believe in continuous learning and staying updated with the latest 
                  industry trends. My approach combines theoretical knowledge with practical 
                  application, always focusing on writing maintainable and well-documented code.
                </p>
              </div>

              {/* Current Focus */}
              <div className="space-y-3">
                <h4 className="font-semibold text-slate-200">Currently Focused On:</h4>
                <div className="flex flex-wrap gap-2">
                  {['Microservices Architecture', 'Spring Security', 'Docker', 'Cloud Deployment', 'System Design'].map((focus, index) => (
                    <Badge 
                      key={focus} 
                      variant="outline" 
                      className="border-teal-400/30 text-teal-300 bg-teal-900/20 hover:bg-teal-800/30 hover:border-teal-300 hover-glow-teal transition-all duration-300"
                      style={{ animationDelay: `${index * 0.1}s` }}
                    >
                      {focus}
                    </Badge>
                  ))}
                </div>
              </div>
            </div>
          </div>

          {/* Highlights Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            {highlights.map((highlight, index) => (
              <Card 
                key={index} 
                className="p-6 glass-dark border-slate-700/50 hover-glow-blue transition-all duration-500 gradient-border slide-in-delayed"
                style={{ animationDelay: `${index * 0.15}s` }}
              >
                <CardContent className="p-0">
                  <div className="flex items-start space-x-4">
                    <div className="text-3xl">{highlight.icon}</div>
                    <div>
                      <h4 className="text-lg font-semibold text-white mb-3">
                        {highlight.title}
                      </h4>
                      <p className="text-slate-300 leading-relaxed">
                        {highlight.description}
                      </p>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </div>
    </section>
  );
};

export default AboutSection;