import React from 'react';
import { Award, ExternalLink, Calendar, CheckCircle } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '../ui/card';
import { Badge } from '../ui/badge';
import { Button } from '../ui/button';
import { portfolioData } from '../../data/mock';

const CertificationsSection = () => {
  const handleVerify = (verifyUrl) => {
    window.open(verifyUrl, '_blank');
  };

  return (
    <section id="certifications" className="py-20 bg-slate-50">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              Certifications
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Professional{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Certifications
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto">
              Industry-recognized certifications that validate my expertise in Java development, 
              Spring Framework, database management, and version control systems.
            </p>
          </div>

          {/* Certifications Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-16">
            {portfolioData.certifications.map((cert) => (
              <Card key={cert.id} className="group hover:shadow-xl transition-all duration-300 overflow-hidden">
                <CardHeader className="pb-4">
                  <div className="flex items-start justify-between">
                    <div className="flex items-center space-x-3">
                      <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-teal-500 rounded-lg flex items-center justify-center">
                        <Award size={24} className="text-white" />
                      </div>
                      <Badge variant="outline" className="border-green-200 text-green-700 bg-green-50">
                        <CheckCircle size={14} className="mr-1" />
                        Verified
                      </Badge>
                    </div>
                  </div>
                  <CardTitle className="text-xl text-slate-800 group-hover:text-blue-600 transition-colors">
                    {cert.name}
                  </CardTitle>
                </CardHeader>

                <CardContent>
                  <div className="space-y-4">
                    {/* Issuer Info */}
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-semibold text-slate-700">
                          {cert.issuer}
                        </p>
                        <div className="flex items-center space-x-2 mt-1">
                          <Calendar size={14} className="text-slate-500" />
                          <span className="text-sm text-slate-600">{cert.date}</span>
                        </div>
                      </div>
                    </div>

                    {/* Credential ID */}
                    <div className="p-3 bg-slate-50 rounded-lg border">
                      <div className="flex items-center justify-between">
                        <div>
                          <p className="text-xs text-slate-500 uppercase tracking-wide font-semibold">
                            Credential ID
                          </p>
                          <p className="font-mono text-sm text-slate-700 mt-1">
                            {cert.credentialId}
                          </p>
                        </div>
                      </div>
                    </div>

                    {/* Verify Button */}
                    <Button
                      onClick={() => handleVerify(cert.verifyUrl)}
                      variant="outline"
                      className="w-full border-blue-200 hover:bg-blue-50 hover:text-blue-600"
                    >
                      <ExternalLink size={16} className="mr-2" />
                      Verify Certificate
                    </Button>
                  </div>
                </CardContent>

                {/* Hover Effect */}
                <div className="absolute inset-0 bg-gradient-to-r from-blue-500/5 to-teal-500/5 opacity-0 group-hover:opacity-100 transition-opacity duration-300 pointer-events-none"></div>
              </Card>
            ))}
          </div>

          {/* Certification Stats */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-16">
            <Card className="p-6 text-center bg-gradient-to-br from-blue-50 to-blue-100 border-blue-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-blue-700 mb-2">4</div>
                <div className="text-blue-800 font-semibold mb-1">Certifications</div>
                <div className="text-blue-600 text-sm">Completed</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-teal-50 to-teal-100 border-teal-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-teal-700 mb-2">2024</div>
                <div className="text-teal-800 font-semibold mb-1">Latest Year</div>
                <div className="text-teal-600 text-sm">Achieved</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-orange-50 to-orange-100 border-orange-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-orange-700 mb-2">100%</div>
                <div className="text-orange-800 font-semibold mb-1">Pass Rate</div>
                <div className="text-orange-600 text-sm">Success Rate</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-purple-50 to-purple-100 border-purple-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-purple-700 mb-2">3</div>
                <div className="text-purple-800 font-semibold mb-1">Technology</div>
                <div className="text-purple-600 text-sm">Areas Covered</div>
              </CardContent>
            </Card>
          </div>

          {/* Certification Categories */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <Card className="p-6 bg-gradient-to-br from-blue-50 to-blue-100 border-blue-200">
              <CardContent className="p-0 text-center">
                <div className="mb-4">
                  <div className="w-16 h-16 bg-blue-600 rounded-full flex items-center justify-center mx-auto">
                    <span className="text-white text-2xl font-bold">J</span>
                  </div>
                </div>
                <h3 className="text-lg font-bold text-blue-800 mb-2">Java Development</h3>
                <p className="text-blue-700 text-sm mb-4">
                  Oracle Certified Associate in Java SE 11, demonstrating core Java programming skills
                </p>
                <Badge className="bg-blue-600 text-white">
                  Oracle Certified
                </Badge>
              </CardContent>
            </Card>

            <Card className="p-6 bg-gradient-to-br from-teal-50 to-teal-100 border-teal-200">
              <CardContent className="p-0 text-center">
                <div className="mb-4">
                  <div className="w-16 h-16 bg-teal-600 rounded-full flex items-center justify-center mx-auto">
                    <span className="text-white text-2xl font-bold">S</span>
                  </div>
                </div>
                <h3 className="text-lg font-bold text-teal-800 mb-2">Spring Framework</h3>
                <p className="text-teal-700 text-sm mb-4">
                  Certified in Spring Boot and Spring Framework for enterprise application development
                </p>
                <Badge className="bg-teal-600 text-white">
                  Pivotal Certified
                </Badge>
              </CardContent>
            </Card>

            <Card className="p-6 bg-gradient-to-br from-orange-50 to-orange-100 border-orange-200">
              <CardContent className="p-0 text-center">
                <div className="mb-4">
                  <div className="w-16 h-16 bg-orange-600 rounded-full flex items-center justify-center mx-auto">
                    <span className="text-white text-2xl font-bold">D</span>
                  </div>
                </div>
                <h3 className="text-lg font-bold text-orange-800 mb-2">Database & Tools</h3>
                <p className="text-orange-700 text-sm mb-4">
                  PostgreSQL DBA and Git version control certifications for development workflow
                </p>
                <Badge className="bg-orange-600 text-white">
                  Industry Certified
                </Badge>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </section>
  );
};

export default CertificationsSection;