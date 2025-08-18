import React from 'react';
import { Award, Target, Users, Zap } from 'lucide-react';
import { Card, CardContent } from '../ui/card';
import { Badge } from '../ui/badge';
import { portfolioData } from '../../data/mock';

const AboutSection = () => {
  const stats = [
    { icon: Award, label: 'Projects Completed', value: '15+' },
    { icon: Target, label: 'Technologies Mastered', value: '12+' },
    { icon: Users, label: 'Team Collaborations', value: '8+' },
    { icon: Zap, label: 'Years Learning', value: '3+' }
  ];

  const highlights = [
    {
      title: "Backend Specialist",
      description: "Expert in Java and Spring Boot development with focus on building scalable REST APIs and microservices architecture."
    },
    {
      title: "Database Management",
      description: "Proficient in PostgreSQL database design, optimization, and management with experience in complex queries and performance tuning."
    },
    {
      title: "Full-Stack Capable",
      description: "Strong frontend skills in React and modern JavaScript, enabling end-to-end application development and seamless integration."
    },
    {
      title: "Version Control & Collaboration",
      description: "Experienced in Git workflows, code reviews, and agile development practices with strong team collaboration skills."
    }
  ];

  return (
    <section id="about" className="py-20 bg-white">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              About Me
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Passionate About Building{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Robust Solutions
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto leading-relaxed">
              I'm a Computer Science student with a deep passion for backend development and 
              database management. My journey in software development has been driven by curiosity 
              and a commitment to writing clean, efficient code that solves real-world problems.
            </p>
          </div>

          {/* Stats Grid */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-16">
            {stats.map((stat, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow">
                <CardContent className="p-0">
                  <div className="mb-4">
                    <div className="w-12 h-12 bg-gradient-to-r from-blue-100 to-teal-100 rounded-lg flex items-center justify-center mx-auto">
                      <stat.icon size={24} className="text-blue-600" />
                    </div>
                  </div>
                  <div className="text-2xl font-bold text-slate-800 mb-2">
                    {stat.value}
                  </div>
                  <div className="text-sm text-slate-600 font-medium">
                    {stat.label}
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>

          {/* Detailed About */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            {/* Image */}
            <div className="order-2 lg:order-1">
              <div className="relative">
                <img
                  src="https://images.unsplash.com/photo-1571171637578-41bc2dd41cd2?w=600&h=400&fit=crop"
                  alt="Developer workspace"
                  className="rounded-2xl shadow-xl w-full h-80 object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-r from-blue-600/20 to-teal-600/20 rounded-2xl"></div>
              </div>
            </div>

            {/* Content */}
            <div className="order-1 lg:order-2 space-y-6">
              <div>
                <h3 className="text-2xl font-bold text-slate-800 mb-4">
                  My Development Journey
                </h3>
                <p className="text-slate-600 leading-relaxed mb-6">
                  Starting my computer science journey in 2022, I quickly developed a passion 
                  for backend development and database systems. What began as curiosity about 
                  how applications work has evolved into expertise in building scalable, 
                  efficient systems using modern technologies.
                </p>
                <p className="text-slate-600 leading-relaxed">
                  I believe in continuous learning and staying updated with the latest 
                  industry trends. My approach combines theoretical knowledge with practical 
                  application, always focusing on writing maintainable and well-documented code.
                </p>
              </div>

              {/* Current Focus */}
              <div className="space-y-3">
                <h4 className="font-semibold text-slate-800">Currently Focused On:</h4>
                <div className="flex flex-wrap gap-2">
                  {['Microservices Architecture', 'Spring Security', 'Docker', 'Cloud Deployment', 'System Design'].map((focus) => (
                    <Badge 
                      key={focus} 
                      variant="outline" 
                      className="border-blue-200 text-blue-700 hover:bg-blue-50"
                    >
                      {focus}
                    </Badge>
                  ))}
                </div>
              </div>
            </div>
          </div>

          {/* Highlights Grid */}
          <div className="mt-16 grid grid-cols-1 md:grid-cols-2 gap-8">
            {highlights.map((highlight, index) => (
              <Card key={index} className="p-6 hover:shadow-lg transition-shadow border-l-4 border-blue-500">
                <CardContent className="p-0">
                  <h4 className="text-lg font-semibold text-slate-800 mb-3">
                    {highlight.title}
                  </h4>
                  <p className="text-slate-600 leading-relaxed">
                    {highlight.description}
                  </p>
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