import React, { useState } from 'react';
import { Mail, Phone, MapPin, Send, Github, Linkedin, Clock } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '../ui/card';
import { Badge } from '../ui/badge';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Textarea } from '../ui/textarea';
import { Label } from '../ui/label';
import { useToast } from '../../hooks/use-toast';
import { portfolioData } from '../../data/mock';

const ContactSection = () => {
  const { toast } = useToast();
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    subject: '',
    message: ''
  });
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    // Mock form submission
    setTimeout(() => {
      setIsSubmitting(false);
      toast({
        title: "Message Sent!",
        description: "Thank you for your message. I'll get back to you soon!",
      });
      setFormData({ name: '', email: '', subject: '', message: '' });
    }, 1500);
  };

  const contactInfo = [
    {
      icon: Mail,
      label: 'Email',
      value: portfolioData.personal.email,
      href: `mailto:${portfolioData.personal.email}`,
      color: 'blue'
    },
    {
      icon: Phone,
      label: 'Phone',
      value: portfolioData.personal.phone,
      href: `tel:${portfolioData.personal.phone}`,
      color: 'teal'
    },
    {
      icon: MapPin,
      label: 'Location',
      value: portfolioData.personal.location,
      href: '#',
      color: 'orange'
    }
  ];

  return (
    <section id="contact" className="py-20 bg-slate-50">
      <div className="container mx-auto px-4">
        <div className="max-w-6xl mx-auto">
          {/* Section Header */}
          <div className="text-center mb-16">
            <Badge variant="secondary" className="mb-4 bg-blue-100 text-blue-700">
              Get In Touch
            </Badge>
            <h2 className="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
              Let's{' '}
              <span className="bg-gradient-to-r from-blue-600 to-teal-600 bg-clip-text text-transparent">
                Connect
              </span>
            </h2>
            <p className="text-lg text-slate-600 max-w-3xl mx-auto">
              I'm always open to discussing new opportunities, interesting projects, 
              or just having a conversation about technology and development.
            </p>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12">
            {/* Contact Information */}
            <div className="space-y-8">
              {/* Availability Status */}
              <Card className="p-6 bg-gradient-to-r from-green-50 to-emerald-50 border-green-200">
                <CardContent className="p-0">
                  <div className="flex items-center space-x-3 mb-4">
                    <div className="w-3 h-3 bg-green-500 rounded-full animate-pulse"></div>
                    <Badge className="bg-green-100 text-green-700">
                      Currently Available
                    </Badge>
                  </div>
                  <h3 className="text-lg font-bold text-green-800 mb-2">
                    {portfolioData.contact.availability}
                  </h3>
                  <div className="flex items-center space-x-2 text-sm text-green-700">
                    <Clock size={16} />
                    <span>{portfolioData.contact.responseTime}</span>
                  </div>
                </CardContent>
              </Card>

              {/* Contact Methods */}
              <div className="space-y-4">
                <h3 className="text-xl font-bold text-slate-800 mb-4">
                  Contact Information
                </h3>
                {contactInfo.map((contact, index) => (
                  <Card key={index} className="group hover:shadow-lg transition-all duration-300">
                    <CardContent className="p-4">
                      <a 
                        href={contact.href}
                        className="flex items-center space-x-4"
                      >
                        <div className={`w-12 h-12 bg-gradient-to-r from-${contact.color}-500 to-${contact.color}-600 rounded-lg flex items-center justify-center`}>
                          <contact.icon size={20} className="text-white" />
                        </div>
                        <div>
                          <p className="text-sm text-slate-500 font-medium uppercase tracking-wide">
                            {contact.label}
                          </p>
                          <p className="text-slate-800 font-semibold group-hover:text-blue-600 transition-colors">
                            {contact.value}
                          </p>
                        </div>
                      </a>
                    </CardContent>
                  </Card>
                ))}
              </div>

              {/* Social Links */}
              <Card className="p-6">
                <CardHeader className="p-0 mb-4">
                  <CardTitle className="text-lg">Find Me Online</CardTitle>
                </CardHeader>
                <CardContent className="p-0">
                  <div className="flex space-x-4">
                    <a
                      href={portfolioData.social.github}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="flex-1"
                    >
                      <Button variant="outline" className="w-full hover:bg-slate-50">
                        <Github size={18} className="mr-2" />
                        GitHub
                      </Button>
                    </a>
                    <a
                      href={portfolioData.social.linkedin}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="flex-1"
                    >
                      <Button variant="outline" className="w-full hover:bg-blue-50 hover:text-blue-600">
                        <Linkedin size={18} className="mr-2" />
                        LinkedIn
                      </Button>
                    </a>
                  </div>
                </CardContent>
              </Card>

              {/* Quick Info */}
              <Card className="p-6 bg-gradient-to-r from-blue-50 to-teal-50 border-blue-200">
                <CardContent className="p-0">
                  <h3 className="text-lg font-bold text-slate-800 mb-4">
                    Quick Info
                  </h3>
                  <div className="space-y-2 text-sm">
                    <div className="flex justify-between">
                      <span className="text-slate-600">Timezone:</span>
                      <span className="text-slate-800 font-semibold">
                        {portfolioData.contact.timezone}
                      </span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-slate-600">Preferred Contact:</span>
                      <span className="text-slate-800 font-semibold capitalize">
                        {portfolioData.contact.preferredContact}
                      </span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-slate-600">Response Time:</span>
                      <span className="text-slate-800 font-semibold">
                        Within 24 hours
                      </span>
                    </div>
                  </div>
                </CardContent>
              </Card>
            </div>

            {/* Contact Form */}
            <Card className="p-8">
              <CardHeader className="p-0 mb-6">
                <CardTitle className="text-2xl text-slate-800">
                  Send Me a Message
                </CardTitle>
                <p className="text-slate-600">
                  Have a question or want to work together? I'd love to hear from you.
                </p>
              </CardHeader>

              <CardContent className="p-0">
                <form onSubmit={handleSubmit} className="space-y-6">
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div className="space-y-2">
                      <Label htmlFor="name">Name *</Label>
                      <Input
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        placeholder="Your full name"
                        required
                      />
                    </div>
                    <div className="space-y-2">
                      <Label htmlFor="email">Email *</Label>
                      <Input
                        id="email"
                        name="email"
                        type="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        placeholder="your.email@example.com"
                        required
                      />
                    </div>
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="subject">Subject *</Label>
                    <Input
                      id="subject"
                      name="subject"
                      value={formData.subject}
                      onChange={handleInputChange}
                      placeholder="What's this about?"
                      required
                    />
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="message">Message *</Label>
                    <Textarea
                      id="message"
                      name="message"
                      value={formData.message}
                      onChange={handleInputChange}
                      placeholder="Tell me more about your project or question..."
                      rows={5}
                      required
                    />
                  </div>

                  <Button
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full bg-blue-600 hover:bg-blue-700 text-white"
                  >
                    {isSubmitting ? (
                      "Sending..."
                    ) : (
                      <>
                        <Send size={18} className="mr-2" />
                        Send Message
                      </>
                    )}
                  </Button>

                  <p className="text-xs text-slate-500 text-center">
                    I'll get back to you as soon as possible. Usually within 24 hours.
                  </p>
                </form>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ContactSection;