# 🔧 Configuration Files

This folder contains all configuration files for the R-DGTT Portail infrastructure.

## 📁 Structure

```
config/
├── traefik/           # Traefik API Gateway configuration
│   ├── traefik.yml    # Static configuration
│   └── dynamic/       # Dynamic configuration
├── consul/            # Consul Service Discovery configuration
│   └── *.json         # Service definitions
├── logstash/          # Logstash configuration
│   ├── pipeline/      # Pipeline configurations
│   └── config/        # General configuration
├── modsecurity/       # ModSecurity WAF configuration
├── fail2ban/          # Fail2Ban configuration
└── trivy/             # Trivy vulnerability scanner configuration
```

## 🚀 Usage

These configurations are automatically loaded by Docker Compose when you run:

```bash
./scripts/deploy-complete.sh
```

## 📝 Notes

- All configuration files are mounted as read-only volumes in containers
- Changes to configuration files require container restart
- Backup these files before making changes
- Follow the complete setup guide in `docs/complete-infrastructure-setup.md`
