import React from 'react';
import { GraduationCap, Calendar, MapPin, Award, BookOpen } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '../ui/card';
import { Badge } from '../ui/badge';
import { portfolioData } from '../../data/mock';

const EducationSection = () => {
  return (
    <section id="education" className="py-20 bg-white">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              Education
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Academic{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Background
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto">
              My educational journey in Computer Science, building a strong foundation 
              in software development, algorithms, and database systems.
            </p>
          </div>

          {/* Education Timeline */}
          <div className="space-y-8">
            {portfolioData.education.map((edu, index) => (
              <Card key={edu.id} className="relative overflow-hidden group hover:shadow-lg transition-all duration-300">
                {/* Timeline connector */}
                {index < portfolioData.education.length - 1 && (
                  <div className="absolute left-8 top-32 w-0.5 h-16 bg-gradient-to-b from-blue-500 to-teal-500 hidden md:block"></div>
                )}
                
                <CardContent className="p-8">
                  <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
                    {/* Institution Info */}
                    <div className="md:col-span-1">
                      <div className="flex items-center space-x-2 mb-4">
                        <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-teal-500 rounded-full flex items-center justify-center">
                          <GraduationCap size={24} className="text-white" />
                        </div>
                      </div>
                      
                      <div className="space-y-3 text-sm text-slate-600">
                        <div className="flex items-center space-x-2">
                          <Calendar size={14} />
                          <span>{edu.duration}</span>
                        </div>
                        <div className="flex items-center space-x-2">
                          <MapPin size={14} />
                          <span>{edu.location}</span>
                        </div>
                        {edu.cgpa && (
                          <div className="flex items-center space-x-2">
                            <Award size={14} />
                            <span>CGPA: {edu.cgpa}</span>
                          </div>
                        )}
                        {edu.percentage && (
                          <div className="flex items-center space-x-2">
                            <Award size={14} />
                            <span>{edu.percentage}</span>
                          </div>
                        )}
                      </div>
                    </div>

                    {/* Education Details */}
                    <div className="md:col-span-3">
                      <div className="mb-6">
                        <h3 className="text-xl font-bold text-slate-800 mb-2">
                          {edu.degree}
                        </h3>
                        <h4 className="text-lg text-blue-600 font-semibold mb-2">
                          {edu.institution}
                        </h4>
                      </div>

                      {/* Relevant Courses */}
                      {edu.relevant_courses && (
                        <div className="mb-4">
                          <h5 className="font-semibold text-slate-800 mb-3 flex items-center">
                            <BookOpen size={16} className="mr-2" />
                            Relevant Coursework
                          </h5>
                          <div className="grid grid-cols-2 md:grid-cols-3 gap-2">
                            {edu.relevant_courses.map((course, courseIndex) => (
                              <Badge 
                                key={courseIndex} 
                                variant="outline" 
                                className="border-blue-200 text-blue-700 text-xs justify-start"
                              >
                                {course}
                              </Badge>
                            ))}
                          </div>
                        </div>
                      )}

                      {/* Subjects for High School */}
                      {edu.subjects && (
                        <div>
                          <h5 className="font-semibold text-slate-800 mb-3 flex items-center">
                            <BookOpen size={16} className="mr-2" />
                            Core Subjects
                          </h5>
                          <div className="flex flex-wrap gap-2">
                            {edu.subjects.map((subject, subjectIndex) => (
                              <Badge 
                                key={subjectIndex} 
                                variant="outline" 
                                className="border-teal-200 text-teal-700 text-xs"
                              >
                                {subject}
                              </Badge>
                            ))}
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </CardContent>
                
                {/* Hover Effect */}
                <div className="absolute inset-0 bg-gradient-to-r from-blue-500/5 to-teal-500/5 opacity-0 group-hover:opacity-100 transition-opacity duration-300 pointer-events-none"></div>
              </Card>
            ))}
          </div>

          {/* Academic Highlights */}
          <div className="mt-16 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <Card className="p-6 text-center bg-gradient-to-br from-blue-50 to-blue-100 border-blue-200">
              <CardContent className="p-0">
                <GraduationCap size={32} className="mx-auto text-blue-600 mb-3" />
                <div className="text-2xl font-bold text-blue-800 mb-1">B.Tech</div>
                <div className="text-blue-600 text-sm">Computer Science</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-teal-50 to-teal-100 border-teal-200">
              <CardContent className="p-0">
                <Award size={32} className="mx-auto text-teal-600 mb-3" />
                <div className="text-2xl font-bold text-teal-800 mb-1">8.7</div>
                <div className="text-teal-600 text-sm">Current CGPA</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-orange-50 to-orange-100 border-orange-200">
              <CardContent className="p-0">
                <BookOpen size={32} className="mx-auto text-orange-600 mb-3" />
                <div className="text-2xl font-bold text-orange-800 mb-1">6+</div>
                <div className="text-orange-600 text-sm">Core CS Subjects</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-purple-50 to-purple-100 border-purple-200">
              <CardContent className="p-0">
                <Calendar size={32} className="mx-auto text-purple-600 mb-3" />
                <div className="text-2xl font-bold text-purple-800 mb-1">2026</div>
                <div className="text-purple-600 text-sm">Expected Graduation</div>
              </CardContent>
            </Card>
          </div>

          {/* Additional Academic Info */}
          <div className="mt-16">
            <Card className="p-8 bg-gradient-to-r from-slate-50 to-slate-100 border-slate-200">
              <CardContent className="p-0">
                <h3 className="text-xl font-bold text-slate-800 mb-4 text-center">
                  Academic Focus Areas
                </h3>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div>
                    <h4 className="font-semibold text-slate-700 mb-3">Core Computer Science</h4>
                    <ul className="space-y-2 text-sm text-slate-600">
                      <li className="flex items-center">
                        <span className="w-2 h-2 bg-blue-500 rounded-full mr-3"></span>
                        Data Structures & Algorithms
                      </li>
                      <li className="flex items-center">
                        <span className="w-2 h-2 bg-blue-500 rounded-full mr-3"></span>
                        Database Management Systems
                      </li>
                      <li className="flex items-center">
                        <span className="w-2 h-2 bg-blue-500 rounded-full mr-3"></span>
                        Object-Oriented Programming
                      </li>
                    </ul>
                  </div>
                  <div>
                    <h4 className="font-semibold text-slate-700 mb-3">Specialized Areas</h4>
                    <ul className="space-y-2 text-sm text-slate-600">
                      <li className="flex items-center">
                        <span className="w-2 h-2 bg-teal-500 rounded-full mr-3"></span>
                        Web Technologies
                      </li>
                      <li className="flex items-center">
                        <span className="w-2 h-2 bg-teal-500 rounded-full mr-3"></span>
                        Software Engineering
                      </li>
                      <li className="flex items-center">
                        <span className="w-2 h-2 bg-teal-500 rounded-full mr-3"></span>
                        Computer Networks
                      </li>
                    </ul>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </section>
  );
};

export default EducationSection;