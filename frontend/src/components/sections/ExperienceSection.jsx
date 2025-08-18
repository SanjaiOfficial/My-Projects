import React from 'react';
import { Building, Calendar, MapPin, ArrowRight } from 'lucide-react';
import { Card, CardContent, CardHeader } from '../ui/card';
import { Badge } from '../ui/badge';
import { portfolioData } from '../../data/mock';

const ExperienceSection = () => {
  return (
    <section id="experience" className="py-20 bg-slate-50">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              Experience
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Professional{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Journey
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto">
              My professional experience in software development, showcasing growth in 
              backend technologies and collaborative project development.
            </p>
          </div>

          {/* Experience Timeline */}
          <div className="space-y-8">
            {portfolioData.experience.map((exp, index) => (
              <Card key={exp.id} className="relative overflow-hidden group hover:shadow-lg transition-all duration-300">
                {/* Timeline Line */}
                {index < portfolioData.experience.length - 1 && (
                  <div className="absolute left-8 top-20 w-0.5 h-24 bg-gradient-to-b from-blue-500 to-teal-500 hidden md:block"></div>
                )}
                
                <CardContent className="p-8">
                  <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
                    {/* Company Info */}
                    <div className="md:col-span-1">
                      <div className="flex items-center space-x-2 mb-2">
                        <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-teal-500 rounded-lg flex items-center justify-center">
                          <Building size={20} className="text-white" />
                        </div>
                        <div className="flex-1">
                          <h3 className="font-bold text-slate-800">{exp.company}</h3>
                        </div>
                      </div>
                      
                      <div className="space-y-2 text-sm text-slate-600">
                        <div className="flex items-center space-x-2">
                          <Calendar size={14} />
                          <span>{exp.duration}</span>
                        </div>
                        <div className="flex items-center space-x-2">
                          <MapPin size={14} />
                          <span>{exp.location}</span>
                        </div>
                      </div>
                    </div>

                    {/* Experience Details */}
                    <div className="md:col-span-3">
                      <div className="mb-4">
                        <h4 className="text-xl font-bold text-slate-800 mb-2">
                          {exp.title}
                        </h4>
                        <p className="text-slate-600 leading-relaxed">
                          {exp.description}
                        </p>
                      </div>

                      {/* Achievements */}
                      <div>
                        <h5 className="font-semibold text-slate-800 mb-3">Key Achievements:</h5>
                        <div className="grid grid-cols-1 lg:grid-cols-2 gap-3">
                          {exp.achievements.map((achievement, achievementIndex) => (
                            <div 
                              key={achievementIndex}
                              className="flex items-start space-x-2 p-3 bg-blue-50 rounded-lg border border-blue-100"
                            >
                              <ArrowRight size={16} className="text-blue-600 mt-0.5 flex-shrink-0" />
                              <span className="text-sm text-slate-700">{achievement}</span>
                            </div>
                          ))}
                        </div>
                      </div>
                    </div>
                  </div>
                </CardContent>
                
                {/* Hover Effect Gradient */}
                <div className="absolute inset-0 bg-gradient-to-r from-blue-500/5 to-teal-500/5 opacity-0 group-hover:opacity-100 transition-opacity duration-300 pointer-events-none"></div>
              </Card>
            ))}
          </div>

          {/* Experience Summary */}
          <div className="mt-16 grid grid-cols-1 md:grid-cols-3 gap-8">
            <Card className="p-6 text-center bg-gradient-to-br from-blue-50 to-blue-100 border-blue-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-blue-700 mb-2">15+</div>
                <div className="text-blue-800 font-semibold mb-1">API Endpoints</div>
                <div className="text-blue-600 text-sm">Built and deployed</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-teal-50 to-teal-100 border-teal-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-teal-700 mb-2">40%</div>
                <div className="text-teal-800 font-semibold mb-1">Performance</div>
                <div className="text-teal-600 text-sm">Improvement achieved</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-orange-50 to-orange-100 border-orange-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-orange-700 mb-2">500+</div>
                <div className="text-orange-800 font-semibold mb-1">Users Served</div>
                <div className="text-orange-600 text-sm">By applications built</div>
              </CardContent>
            </Card>
          </div>

          {/* Call to Action */}
          <div className="mt-16 text-center">
            <div className="bg-gradient-to-r from-blue-50 to-teal-50 rounded-2xl p-8 border border-blue-100">
              <h3 className="text-2xl font-bold text-slate-800 mb-4">
                Ready to Contribute
              </h3>
              <p className="text-slate-600 mb-6 max-w-2xl mx-auto">
                I'm actively seeking new opportunities to apply my backend development skills 
                and contribute to innovative projects. Let's build something amazing together!
              </p>
              <Badge className="bg-green-100 text-green-700 px-4 py-2">
                Available for Opportunities
              </Badge>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ExperienceSection;