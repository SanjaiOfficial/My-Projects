import React, { useState } from 'react';
import { Code, Database, Wrench, ChevronRight } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '../ui/card';
import { Badge } from '../ui/badge';
import { Progress } from '../ui/progress';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../ui/tabs';
import { portfolioData } from '../../data/mock';

const SkillsSection = () => {
  const [activeCategory, setActiveCategory] = useState('backend');

  const skillCategories = [
    {
      id: 'backend',
      name: 'Backend',
      icon: Database,
      color: 'blue',
      skills: portfolioData.skills.backend
    },
    {
      id: 'frontend',
      name: 'Frontend',
      icon: Code,
      color: 'teal',
      skills: portfolioData.skills.frontend
    },
    {
      id: 'tools',
      name: 'Tools & Others',
      icon: Wrench,
      color: 'orange',
      skills: portfolioData.skills.tools
    }
  ];

  const getSkillIcon = (iconName) => {
    // This is a simplified icon mapping - in a real app you'd import all icons
    const icons = {
      coffee: '☕',
      leaf: '🍃',
      api: '🔗',
      database: '🗄️',
      component: '⚛️',
      code: '💻',
      code2: '</>'
    };
    return icons[iconName] || '🛠️';
  };

  const renderSkillCard = (skill) => (
    <Card key={skill.name} className="hover:shadow-lg transition-all duration-300 group">
      <CardContent className="p-4">
        <div className="flex items-center justify-between mb-3">
          <div className="flex items-center space-x-3">
            <span className="text-2xl">{getSkillIcon(skill.icon)}</span>
            <span className="font-semibold text-slate-800">{skill.name}</span>
          </div>
          <Badge variant="outline" className="text-xs">
            {skill.level}%
          </Badge>
        </div>
        <Progress 
          value={skill.level} 
          className="h-2"
        />
      </CardContent>
    </Card>
  );

  return (
    <section id="skills" className="py-20 bg-slate-50">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              My Skills
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Technical{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Expertise
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto">
              A comprehensive overview of my technical skills and proficiency levels across 
              different technologies and tools I use in my development workflow.
            </p>
          </div>

          {/* Skills Tabs */}
          <Tabs value={activeCategory} onValueChange={setActiveCategory} className="w-full">
            <TabsList className="grid w-full grid-cols-3 mb-8">
              {skillCategories.map((category) => (
                <TabsTrigger 
                  key={category.id}
                  value={category.id}
                  className="flex items-center space-x-2"
                >
                  <category.icon size={18} />
                  <span className="hidden sm:inline">{category.name}</span>
                </TabsTrigger>
              ))}
            </TabsList>

            {skillCategories.map((category) => (
              <TabsContent key={category.id} value={category.id}>
                <Card className="p-6 border-0 shadow-lg">
                  <CardHeader className="pb-6">
                    <div className="flex items-center space-x-3">
                      <div className={`p-3 rounded-lg bg-${category.color}-100`}>
                        <category.icon size={24} className={`text-${category.color}-600`} />
                      </div>
                      <div>
                        <CardTitle className="text-xl text-slate-800">
                          {category.name} Skills
                        </CardTitle>
                        <p className="text-slate-600">
                          {category.id === 'backend' && 'Server-side development and database management'}
                          {category.id === 'frontend' && 'User interface and client-side development'}
                          {category.id === 'tools' && 'Development tools and workflow optimization'}
                        </p>
                      </div>
                    </div>
                  </CardHeader>
                  <CardContent className="p-0">
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                      {category.skills.map(renderSkillCard)}
                    </div>
                  </CardContent>
                </Card>
              </TabsContent>
            ))}
          </Tabs>

          {/* Skill Highlights */}
          <div className="mt-16 grid grid-cols-1 md:grid-cols-3 gap-8">
            <Card className="p-6 text-center bg-gradient-to-br from-blue-50 to-blue-100 border-blue-200">
              <CardContent className="p-0">
                <div className="mb-4">
                  <Database size={32} className="mx-auto text-blue-600" />
                </div>
                <h3 className="text-lg font-bold text-blue-800 mb-2">Backend Focus</h3>
                <p className="text-blue-700 text-sm">
                  Specialized in Java ecosystem with Spring Boot framework and PostgreSQL database management
                </p>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-teal-50 to-teal-100 border-teal-200">
              <CardContent className="p-0">
                <div className="mb-4">
                  <Code size={32} className="mx-auto text-teal-600" />
                </div>
                <h3 className="text-lg font-bold text-teal-800 mb-2">Full-Stack Ready</h3>
                <p className="text-teal-700 text-sm">
                  Capable of building complete applications with React frontend and REST API backend
                </p>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-orange-50 to-orange-100 border-orange-200">
              <CardContent className="p-0">
                <div className="mb-4">
                  <Wrench size={32} className="mx-auto text-orange-600" />
                </div>
                <h3 className="text-lg font-bold text-orange-800 mb-2">Modern Workflow</h3>
                <p className="text-orange-700 text-sm">
                  Proficient with Git, modern IDEs, and development tools for efficient coding practices
                </p>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </section>
  );
};

export default SkillsSection;