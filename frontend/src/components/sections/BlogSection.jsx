import React from 'react';
import { Calendar, Clock, ArrowRight, Tag } from 'lucide-react';
import { Card, CardContent, CardHeader } from '../ui/card';
import { Badge } from '../ui/badge';
import { Button } from '../ui/button';
import { portfolioData } from '../../data/mock';

const BlogSection = () => {
  const handleReadMore = (url) => {
    // Mock navigation - in a real app this would navigate to the blog post
    console.log('Navigate to:', url);
  };

  return (
    <section id="blog" className="py-20 bg-white">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              Blog & Articles
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Latest{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Insights
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto">
              Sharing my knowledge and experiences in backend development, database optimization, 
              and modern web technologies through technical articles and tutorials.
            </p>
          </div>

          {/* Featured Blog Post */}
          <Card className="mb-12 overflow-hidden group hover:shadow-xl transition-all duration-300">
            <div className="grid grid-cols-1 lg:grid-cols-2">
              {/* Image */}
              <div className="relative overflow-hidden">
                <img
                  src={portfolioData.blogs[0].image}
                  alt={portfolioData.blogs[0].title}
                  className="w-full h-64 lg:h-full object-cover group-hover:scale-105 transition-transform duration-300"
                />
                <div className="absolute top-4 left-4">
                  <Badge className="bg-blue-600 text-white">
                    Featured Post
                  </Badge>
                </div>
              </div>

              {/* Content */}
              <CardContent className="p-8 flex flex-col justify-center">
                <div className="mb-4">
                  <div className="flex items-center space-x-4 text-sm text-slate-600 mb-3">
                    <div className="flex items-center space-x-1">
                      <Calendar size={14} />
                      <span>{portfolioData.blogs[0].date}</span>
                    </div>
                    <div className="flex items-center space-x-1">
                      <Clock size={14} />
                      <span>{portfolioData.blogs[0].readTime}</span>
                    </div>
                  </div>
                  
                  <h3 className="text-2xl font-bold text-slate-800 mb-4 group-hover:text-blue-600 transition-colors">
                    {portfolioData.blogs[0].title}
                  </h3>
                  
                  <p className="text-slate-600 leading-relaxed mb-6">
                    {portfolioData.blogs[0].excerpt}
                  </p>

                  <div className="flex flex-wrap gap-2 mb-6">
                    {portfolioData.blogs[0].tags.map((tag) => (
                      <Badge 
                        key={tag} 
                        variant="outline" 
                        className="border-blue-200 text-blue-700 text-xs"
                      >
                        <Tag size={12} className="mr-1" />
                        {tag}
                      </Badge>
                    ))}
                  </div>
                </div>

                <Button
                  onClick={() => handleReadMore(portfolioData.blogs[0].url)}
                  className="bg-blue-600 hover:bg-blue-700 text-white w-fit"
                >
                  Read Full Article
                  <ArrowRight size={16} className="ml-2" />
                </Button>
              </CardContent>
            </div>
          </Card>

          {/* Other Blog Posts */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-12">
            {portfolioData.blogs.slice(1).map((blog) => (
              <Card key={blog.id} className="group hover:shadow-lg transition-all duration-300 overflow-hidden">
                <div className="relative">
                  <img
                    src={blog.image}
                    alt={blog.title}
                    className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-300"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                </div>

                <CardContent className="p-6">
                  <div className="mb-4">
                    <div className="flex items-center space-x-4 text-sm text-slate-600 mb-3">
                      <div className="flex items-center space-x-1">
                        <Calendar size={14} />
                        <span>{blog.date}</span>
                      </div>
                      <div className="flex items-center space-x-1">
                        <Clock size={14} />
                        <span>{blog.readTime}</span>
                      </div>
                    </div>
                    
                    <h4 className="text-lg font-bold text-slate-800 mb-3 group-hover:text-blue-600 transition-colors line-clamp-2">
                      {blog.title}
                    </h4>
                    
                    <p className="text-slate-600 text-sm leading-relaxed mb-4 line-clamp-3">
                      {blog.excerpt}
                    </p>

                    <div className="flex flex-wrap gap-1 mb-4">
                      {blog.tags.slice(0, 2).map((tag) => (
                        <Badge 
                          key={tag} 
                          variant="outline" 
                          className="border-teal-200 text-teal-700 text-xs"
                        >
                          {tag}
                        </Badge>
                      ))}
                    </div>
                  </div>

                  <Button
                    onClick={() => handleReadMore(blog.url)}
                    variant="ghost"
                    className="w-full justify-between hover:bg-blue-50 hover:text-blue-600 p-0 h-auto"
                  >
                    Read More
                    <ArrowRight size={14} />
                  </Button>
                </CardContent>
              </Card>
            ))}
          </div>

          {/* Blog Stats */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-12">
            <Card className="p-6 text-center bg-gradient-to-br from-blue-50 to-blue-100 border-blue-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-blue-700 mb-2">
                  {portfolioData.blogs.length}
                </div>
                <div className="text-blue-800 font-semibold mb-1">Articles Written</div>
                <div className="text-blue-600 text-sm">Technical content</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-teal-50 to-teal-100 border-teal-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-teal-700 mb-2">4+</div>
                <div className="text-teal-800 font-semibold mb-1">Technologies</div>
                <div className="text-teal-600 text-sm">Covered in articles</div>
              </CardContent>
            </Card>

            <Card className="p-6 text-center bg-gradient-to-br from-orange-50 to-orange-100 border-orange-200">
              <CardContent className="p-0">
                <div className="text-3xl font-bold text-orange-700 mb-2">19</div>
                <div className="text-orange-800 font-semibold mb-1">Min Average</div>
                <div className="text-orange-600 text-sm">Reading time</div>
              </CardContent>
            </Card>
          </div>

          {/* Call to Action */}
          <div className="text-center">
            <Card className="p-8 bg-gradient-to-r from-blue-50 to-teal-50 border-blue-100">
              <CardContent className="p-0">
                <h3 className="text-2xl font-bold text-slate-800 mb-4">
                  Want to Read More?
                </h3>
                <p className="text-slate-600 mb-6 max-w-2xl mx-auto">
                  Stay updated with my latest articles on backend development, database optimization, 
                  and modern web technologies. I regularly share insights from my learning journey.
                </p>
                <div className="flex flex-col sm:flex-row gap-4 justify-center">
                  <Button className="bg-blue-600 hover:bg-blue-700 text-white">
                    View All Articles
                  </Button>
                  <Button variant="outline" className="border-blue-200 hover:bg-blue-50">
                    Subscribe to Updates
                  </Button>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </section>
  );
};

export default BlogSection;