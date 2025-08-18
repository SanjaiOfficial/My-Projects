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
      color: 'purple',
      skills: portfolioData.skills.tools
    }
  ];

  const getSkillIcon = (iconName) => {
    const icons = {
      coffee: '☕',
      leaf: '🍃',
      api: '🔗',
      database: '🗄️',
      component: '⚛️',
      code: '💻',
      code2: '</>',
      palette: '🎨',
      paintbrush: '🖌️',
      layout: '📐',
      'git-branch': '🌿',
      github: '🐙',
      send: '📤',
      container: '📦'
    };
    return icons[iconName] || '🛠️';
  };

  const renderSkillCard = (skill, categoryColor) => (
    <Card key={skill.name} className="glass-dark border-slate-700/50 hover-glow-blue transition-all duration-500 group">
      <CardContent className="p-4">
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center space-x-3">
            <span className="text-2xl group-hover:scale-110 transition-transform duration-300">{getSkillIcon(skill.icon)}</span>
            <span className="font-semibold text-white group-hover:text-cyan-400 transition-colors duration-300">{skill.name}</span>
          </div>
          <Badge variant="outline" className={`text-xs border-${categoryColor}-400/30 text-${categoryColor}-300 bg-${categoryColor}-900/20`}>
            {skill.level}%
          </Badge>
        </div>
        <div className="relative">
          <Progress 
            value={skill.level} 
            className="h-3 bg-slate-800 border border-slate-700"
          />
          <div 
            className={`absolute top-0 left-0 h-3 rounded-full bg-gradient-to-r from-${categoryColor}-500 to-${categoryColor}-400 glow-${categoryColor} transition-all duration-1000`}
            style={{ width: `${skill.level}%` }}
          ></div>
        </div>
      </CardContent>
    </Card>
  );

  return (
    <section id="skills" className="py-20 bg-gradient-to-b from-slate-800 to-slate-900 relative overflow-hidden">
      {/* Background effects */}
      <div className="absolute inset-0">
        <div className="absolute top-40 left-10 w-72 h-72 bg-purple-500/5 rounded-full blur-3xl"></div>
        <div className="absolute bottom-40 right-10 w-64 h-64 bg-cyan-500/5 rounded-full blur-3xl"></div>
      </div>

      <div className="container mx-auto px-4 relative z-10">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16 slide-in">
            <Badge variant="secondary" className="mb-6 bg-gradient-to-r from-purple-900/50 to-cyan-900/50 text-purple-400 border border-purple-500/30 hover:glow-purple">
              My Skills
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-white mb-6">
              Technical{' '}
              <span className="neon-text bg-gradient-to-r from-purple-400 via-cyan-500 to-teal-400 bg-clip-text text-transparent">
                Expertise
              </span>
            </h2>
            <p className="text-lg text-slate-300 max-w-3xl mx-auto">
              A comprehensive overview of my technical skills and proficiency levels across 
              different technologies and tools I use in my development workflow.
            </p>
          </div>

          {/* Skills Tabs */}
          <Tabs value={activeCategory} onValueChange={setActiveCategory} className="w-full slide-in-delayed">
            <TabsList className="grid w-full grid-cols-3 mb-8 glass-dark border border-slate-700/50">
              {skillCategories.map((category) => (
                <TabsTrigger 
                  key={category.id}
                  value={category.id}
                  className={`flex items-center space-x-2 text-slate-300 data-[state=active]:text-${category.color}-400 data-[state=active]:bg-${category.color}-900/30 hover:text-${category.color}-400 transition-all duration-300`}
                >
                  <category.icon size={18} />
                  <span className="hidden sm:inline">{category.name}</span>
                </TabsTrigger>
              ))}
            </TabsList>

            {skillCategories.map((category) => (
              <TabsContent key={category.id} value={category.id}>
                <Card className="glass-dark border-slate-700/50 hover-glow-blue transition-all duration-500">
                  <CardHeader className="pb-6">
                    <div className="flex items-center space-x-4">
                      <div className={`p-4 bg-gradient-to-r from-${category.color}-500/20 to-${category.color}-400/20 rounded-lg border border-${category.color}-500/30`}>
                        <category.icon size={32} className={`text-${category.color}-400`} />
                      </div>
                      <div>
                        <CardTitle className="text-2xl text-white">
                          {category.name} Skills
                        </CardTitle>
                        <p className="text-slate-400 mt-2">
                          {category.id === 'backend' && 'Server-side development and database management expertise'}
                          {category.id === 'frontend' && 'User interface and client-side development skills'}
                          {category.id === 'tools' && 'Development tools and workflow optimization knowledge'}
                        </p>
                      </div>
                    </div>
                  </CardHeader>
                  <CardContent className="p-6">
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                      {category.skills.map((skill) => renderSkillCard(skill, category.color))}
                    </div>
                  </CardContent>
                </Card>
              </TabsContent>
            ))}
          </Tabs>

          {/* Skill Highlights */}
          <div className="mt-16 grid grid-cols-1 md:grid-cols-3 gap-8">
            <Card className="p-6 text-center glass-dark border-slate-700/50 hover-glow-blue transition-all duration-500 slide-in-delayed">
              <CardContent className="p-0">
                <div className="mb-4">
                  <Database size={40} className="mx-auto text-blue-400" />
                </div>
                <h3 className="text-lg font-bold text-white mb-3">Backend Focus</h3>
                <p className="text-slate-400 text-sm leading-relaxed">
                  Specialized in Java ecosystem with Spring Boot framework and PostgreSQL database management
                </p>
              </CardContent>
            </Card>

            <Card className="p-6 text-center glass-dark border-slate-700/50 hover-glow-teal transition-all duration-500 slide-in-delayed">
              <CardContent className="p-0">
                <div className="mb-4">
                  <Code size={40} className="mx-auto text-teal-400" />
                </div>
                <h3 className="text-lg font-bold text-white mb-3">Full-Stack Ready</h3>
                <p className="text-slate-400 text-sm leading-relaxed">
                  Capable of building complete applications with React frontend and REST API backend
                </p>
              </CardContent>
            </Card>

            <Card className="p-6 text-center glass-dark border-slate-700/50 hover-glow-purple transition-all duration-500 slide-in-delayed">
              <CardContent className="p-0">
                <div className="mb-4">
                  <Wrench size={40} className="mx-auto text-purple-400" />
                </div>
                <h3 className="text-lg font-bold text-white mb-3">Modern Workflow</h3>
                <p className="text-slate-400 text-sm leading-relaxed">
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