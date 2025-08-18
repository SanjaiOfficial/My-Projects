// Mock data for Sanjai's Portfolio
export const portfolioData = {
  personal: {
    name: "Sanjai",
    title: "Computer Science Student & Full-Stack Developer",
    location: "India",
    email: "sanjai.dev@email.com",
    phone: "+91 98765 43210",
    bio: "Passionate Computer Science student with strong expertise in backend development using Java and Spring Boot. Experienced in building scalable REST APIs, managing PostgreSQL databases, and creating responsive frontend applications. Always eager to learn new technologies and contribute to innovative projects.",
    avatar: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=400&fit=crop&crop=face",
    resume: "/resume-sanjai-cs-developer.pdf"
  },
  
  skills: {
    backend: [
      { name: "Java", level: 90, icon: "coffee" },
      { name: "Spring Boot", level: 85, icon: "leaf" },
      { name: "REST APIs", level: 88, icon: "api" },
      { name: "PostgreSQL", level: 82, icon: "database" },
      { name: "MySQL", level: 78, icon: "database" },
      { name: "MongoDB", level: 75, icon: "database" }
    ],
    frontend: [
      { name: "React.js", level: 80, icon: "component" },
      { name: "JavaScript", level: 85, icon: "code" },
      { name: "HTML5", level: 90, icon: "code2" },
      { name: "CSS3", level: 85, icon: "palette" },
      { name: "Tailwind CSS", level: 80, icon: "paintbrush" },
      { name: "Bootstrap", level: 75, icon: "layout" }
    ],
    tools: [
      { name: "Git", level: 88, icon: "git-branch" },
      { name: "GitHub", level: 85, icon: "github" },
      { name: "IntelliJ IDEA", level: 85, icon: "code" },
      { name: "VS Code", level: 90, icon: "code2" },
      { name: "Postman", level: 82, icon: "send" },
      { name: "Docker", level: 70, icon: "container" }
    ]
  },

  projects: [
    {
      id: 1,
      title: "E-Commerce REST API",
      description: "Comprehensive REST API for an e-commerce platform built with Spring Boot and PostgreSQL. Features user authentication, product management, shopping cart, and order processing with secure payment integration.",
      image: "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=600&h=400&fit=crop",
      technologies: ["Spring Boot", "PostgreSQL", "JWT", "Spring Security", "Maven"],
      githubUrl: "https://github.com/sanjai/ecommerce-api",
      liveUrl: "https://ecommerce-api-sanjai.herokuapp.com",
      highlights: [
        "RESTful API design with 25+ endpoints",
        "JWT-based authentication & authorization",
        "Database optimization with indexing",
        "Comprehensive error handling & logging"
      ],
      category: "Backend"
    },
    {
      id: 2,
      title: "Task Management Web App",
      description: "Full-stack task management application with React frontend and Spring Boot backend. Features real-time updates, user collaboration, project categorization, and deadline tracking.",
      image: "https://images.unsplash.com/photo-1611224923853-80b023f02d71?w=600&h=400&fit=crop",
      technologies: ["React", "Spring Boot", "PostgreSQL", "WebSocket", "Tailwind CSS"],
      githubUrl: "https://github.com/sanjai/taskmanager-fullstack",
      liveUrl: "https://taskmanager-sanjai.netlify.app",
      highlights: [
        "Real-time collaboration with WebSocket",
        "Responsive React frontend",
        "RESTful backend with Spring Boot",
        "Advanced filtering and search capabilities"
      ],
      category: "Full-Stack"
    },
    {
      id: 3,
      title: "Student Management System",
      description: "University student management system with course enrollment, grade tracking, and administrative features. Built with Spring Boot, PostgreSQL, and includes comprehensive reporting.",
      image: "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=600&h=400&fit=crop",
      technologies: ["Spring Boot", "PostgreSQL", "Thymeleaf", "Spring Data JPA", "Chart.js"],
      githubUrl: "https://github.com/sanjai/student-management",
      liveUrl: "https://student-mgmt-sanjai.onrender.com",
      highlights: [
        "Complex relational database design",
        "Role-based access control",
        "Automated report generation",
        "Data visualization with charts"
      ],
      category: "Backend"
    },
    {
      id: 4,
      title: "Personal Finance Tracker",
      description: "React-based personal finance tracking application with expense categorization, budget planning, and financial goal setting. Features interactive charts and monthly reports.",
      image: "https://images.unsplash.com/photo-1554224155-6726b3ff858f?w=600&h=400&fit=crop",
      technologies: ["React", "Chart.js", "Local Storage", "CSS3", "JavaScript ES6"],
      githubUrl: "https://github.com/sanjai/finance-tracker",
      liveUrl: "https://finance-tracker-sanjai.vercel.app",
      highlights: [
        "Interactive data visualization",
        "Responsive design for mobile use",
        "Local storage for data persistence",
        "Budget tracking and alerts"
      ],
      category: "Frontend"
    }
  ],

  education: [
    {
      id: 1,
      degree: "Bachelor of Technology in Computer Science",
      institution: "Saveetha Engineering College",
      duration: "2022 - 2026",
      location: "India",
      cgpa: "8.7/10",
      relevant_courses: [
        "Data Structures & Algorithms",
        "Database Management Systems", 
        "Object-Oriented Programming",
        "Web Technologies",
        "Software Engineering",
        "Computer Networks"
      ]
    },
    {
      id: 2,
      degree: "Higher Secondary Certificate",
      institution: "Elite Matric Hr.Sec.School",
      duration: "2020 - 2022",
      location: "India",
      percentage: "94.2%",
      subjects: ["Physics", "Chemistry", "Mathematics", "Computer Science"]
    }
  ],

  certifications: [
    {
      id: 1,
      name: "Oracle Certified Associate Java SE 11 Developer",
      issuer: "Oracle",
      date: "March 2024",
      credentialId: "OCA-J11-2024-SJ789",
      verifyUrl: "https://oracle.com/verify/OCA-J11-2024-SJ789"
    },
    {
      id: 2,
      name: "Spring Framework and Spring Boot Certification",
      issuer: "Pivotal/VMware",
      date: "February 2024",
      credentialId: "SPR-BOOT-2024-456",
      verifyUrl: "https://pivotal.io/verify/SPR-BOOT-2024-456"
    },
    {
      id: 3,
      name: "PostgreSQL Database Administration",
      issuer: "PostgreSQL Training Institute",
      date: "January 2024",
      credentialId: "PG-DBA-2024-123",
      verifyUrl: "https://postgresql-training.org/verify/PG-DBA-2024-123"
    },
    {
      id: 4,
      name: "Git Version Control System",
      issuer: "Atlassian",
      date: "December 2023",
      credentialId: "GIT-VC-2023-789",
      verifyUrl: "https://atlassian.com/verify/GIT-VC-2023-789"
    }
  ],

  blogs: [
    {
      id: 1,
      title: "Building Scalable REST APIs with Spring Boot and PostgreSQL",
      excerpt: "A comprehensive guide to creating robust backend services using Spring Boot framework and PostgreSQL database, covering best practices and performance optimization techniques.",
      date: "March 15, 2024",
      readTime: "8 min read",
      image: "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=600&h=300&fit=crop",
      tags: ["Spring Boot", "PostgreSQL", "REST API", "Backend"],
      url: "/blog/scalable-rest-apis-spring-boot"
    },
    {
      id: 2, 
      title: "Database Optimization Techniques for Better Performance",
      excerpt: "Exploring advanced PostgreSQL optimization strategies including indexing, query optimization, and connection pooling to improve application performance.",
      date: "February 28, 2024",
      readTime: "6 min read",
      image: "https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=600&h=300&fit=crop",
      tags: ["PostgreSQL", "Database", "Performance", "Optimization"],
      url: "/blog/database-optimization-techniques"
    },
    {
      id: 3,
      title: "Modern Frontend Development with React and Tailwind CSS",
      excerpt: "Best practices for building responsive and maintainable React applications using Tailwind CSS for rapid UI development and component-based architecture.",
      date: "February 10, 2024", 
      readTime: "5 min read",
      image: "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=600&h=300&fit=crop",
      tags: ["React", "Tailwind CSS", "Frontend", "JavaScript"],
      url: "/blog/modern-frontend-react-tailwind"
    }
  ],

  social: {
    github: "https://github.com/sanjai-dev",
    linkedin: "https://linkedin.com/in/sanjai-cs-developer",
    twitter: "https://twitter.com/sanjai_dev",
    email: "sanjai.dev@email.com"
  },

  contact: {
    availability: "Available for internships and freelance projects",
    preferredContact: "email",
    timezone: "IST (GMT +5:30)",
    responseTime: "Usually responds within 24 hours"
  }
};