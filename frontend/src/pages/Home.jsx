import React from 'react';
import HeroSection from '../components/sections/HeroSection';
import AboutSection from '../components/sections/AboutSection';
import SkillsSection from '../components/sections/SkillsSection';
import ProjectsSection from '../components/sections/ProjectsSection';
import EducationSection from '../components/sections/EducationSection';
import CertificationsSection from '../components/sections/CertificationsSection';
import BlogSection from '../components/sections/BlogSection';
import ContactSection from '../components/sections/ContactSection';

const Home = () => {
  return (
    <div className="min-h-screen bg-slate-900">
      <HeroSection />
      <AboutSection />
      <SkillsSection />
      <ProjectsSection />
      <EducationSection />
      <CertificationsSection />
      <BlogSection />
      <ContactSection />
    </div>
  );
};

export default Home;