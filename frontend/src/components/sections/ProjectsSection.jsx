import React, { useState } from 'react';
import { ExternalLink, Github, Filter, ArrowRight } from 'lucide-react';
import { Card, CardContent, CardHeader } from '../ui/card';
import { Badge } from '../ui/badge';
import { Button } from '../ui/button';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../ui/tabs';
import { portfolioData } from '../../data/mock';

const ProjectsSection = () => {
  const [selectedProject, setSelectedProject] = useState(null);
  
  const categories = ['All', 'Backend', 'Full-Stack', 'Frontend'];
  const [activeCategory, setActiveCategory] = useState('All');

  const filteredProjects = activeCategory === 'All' 
    ? portfolioData.projects 
    : portfolioData.projects.filter(project => project.category === activeCategory);

  const openProjectModal = (project) => {
    setSelectedProject(project);
  };

  const closeProjectModal = () => {
    setSelectedProject(null);
  };

  return (
    <section id="projects" className="py-20 bg-white">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              My Projects
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Featured{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Work
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto">
              A collection of projects showcasing my skills in backend development, 
              database management, and full-stack application development.
            </p>
          </div>

          {/* Category Filter */}
          <Tabs value={activeCategory} onValueChange={setActiveCategory} className="mb-12">
            <TabsList className="grid w-full grid-cols-4 max-w-2xl mx-auto">
              {categories.map((category) => (
                <TabsTrigger key={category} value={category} className="text-sm">
                  {category}
                </TabsTrigger>
              ))}
            </TabsList>
          </Tabs>

          {/* Projects Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            {filteredProjects.map((project) => (
              <Card key={project.id} className="group overflow-hidden hover:shadow-xl transition-all duration-300">
                <div className="relative overflow-hidden">
                  <img
                    src={project.image}
                    alt={project.title}
                    className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-300"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                    <div className="absolute bottom-4 left-4 right-4">
                      <div className="flex space-x-2">
                        <Button
                          size="sm"
                          variant="secondary"
                          onClick={() => window.open(project.githubUrl, '_blank')}
                        >
                          <Github size={16} className="mr-1" />
                          Code
                        </Button>
                        <Button
                          size="sm"
                          onClick={() => window.open(project.liveUrl, '_blank')}
                        >
                          <ExternalLink size={16} className="mr-1" />
                          Live
                        </Button>
                      </div>
                    </div>
                  </div>
                  <Badge 
                    className="absolute top-4 left-4 bg-white/90 text-slate-700"
                  >
                    {project.category}
                  </Badge>
                </div>

                <CardContent className="p-6">
                  <h3 className="text-xl font-bold text-slate-800 mb-3 group-hover:text-blue-600 transition-colors">
                    {project.title}
                  </h3>
                  
                  <p className="text-slate-600 mb-4 line-clamp-3">
                    {project.description}
                  </p>

                  <div className="flex flex-wrap gap-2 mb-4">
                    {project.technologies.slice(0, 3).map((tech) => (
                      <Badge 
                        key={tech} 
                        variant="outline" 
                        className="text-xs border-blue-200 text-blue-700"
                      >
                        {tech}
                      </Badge>
                    ))}
                    {project.technologies.length > 3 && (
                      <Badge variant="outline" className="text-xs">
                        +{project.technologies.length - 3} more
                      </Badge>
                    )}
                  </div>

                  <Button
                    variant="ghost"
                    onClick={() => openProjectModal(project)}
                    className="w-full justify-between hover:bg-blue-50 hover:text-blue-600"
                  >
                    View Details
                    <ArrowRight size={16} />
                  </Button>
                </CardContent>
              </Card>
            ))}
          </div>

          {/* Load More Button */}
          <div className="text-center mt-12">
            <Button 
              variant="outline" 
              size="lg"
              className="border-blue-200 hover:bg-blue-50 hover:text-blue-600"
            >
              View All Projects
              <ArrowRight size={18} className="ml-2" />
            </Button>
          </div>
        </div>
      </div>

      {/* Project Modal */}
      {selectedProject && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
          <Card className="max-w-4xl w-full max-h-[90vh] overflow-y-auto">
            <CardHeader className="pb-4">
              <div className="flex items-start justify-between">
                <div>
                  <h3 className="text-2xl font-bold text-slate-800 mb-2">
                    {selectedProject.title}
                  </h3>
                  <Badge className="bg-blue-100 text-blue-700">
                    {selectedProject.category}
                  </Badge>
                </div>
                <Button
                  variant="ghost"
                  size="sm"
                  onClick={closeProjectModal}
                  className="text-slate-500 hover:text-slate-700"
                >
                  ✕
                </Button>
              </div>
            </CardHeader>

            <CardContent>
              <img
                src={selectedProject.image}
                alt={selectedProject.title}
                className="w-full h-64 object-cover rounded-lg mb-6"
              />

              <p className="text-slate-700 mb-6 leading-relaxed">
                {selectedProject.description}
              </p>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <h4 className="font-semibold text-slate-800 mb-3">Technologies Used</h4>
                  <div className="flex flex-wrap gap-2">
                    {selectedProject.technologies.map((tech) => (
                      <Badge key={tech} variant="outline" className="border-blue-200 text-blue-700">
                        {tech}
                      </Badge>
                    ))}
                  </div>
                </div>

                <div>
                  <h4 className="font-semibold text-slate-800 mb-3">Key Features</h4>
                  <ul className="space-y-2">
                    {selectedProject.highlights.map((highlight, index) => (
                      <li key={index} className="text-sm text-slate-600 flex items-start">
                        <span className="w-1.5 h-1.5 bg-blue-500 rounded-full mt-2 mr-2 flex-shrink-0"></span>
                        {highlight}
                      </li>
                    ))}
                  </ul>
                </div>
              </div>

              <div className="flex space-x-4 mt-6">
                <Button
                  onClick={() => window.open(selectedProject.githubUrl, '_blank')}
                  className="flex-1 bg-slate-800 hover:bg-slate-900"
                >
                  <Github size={18} className="mr-2" />
                  View Source Code
                </Button>
                <Button
                  onClick={() => window.open(selectedProject.liveUrl, '_blank')}
                  variant="outline"
                  className="flex-1"
                >
                  <ExternalLink size={18} className="mr-2" />
                  Live Demo
                </Button>
              </div>
            </CardContent>
          </Card>
        </div>
      )}
    </section>
  );
};

export default ProjectsSection;